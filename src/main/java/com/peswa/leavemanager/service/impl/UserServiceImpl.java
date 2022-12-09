package com.peswa.leavemanager.service.impl;

import com.peswa.leavemanager.dto.request.LoginRequest;
import com.peswa.leavemanager.dto.request.ResetPasswordRequest;
import com.peswa.leavemanager.dto.request.UserGroupRequest;
import com.peswa.leavemanager.dto.request.UserRequest;
import com.peswa.leavemanager.dto.response.LoginResponse;
import com.peswa.leavemanager.dto.response.UserGroupResponse;
import com.peswa.leavemanager.dto.response.UserGroupingResponse;
import com.peswa.leavemanager.dto.response.UserResponse;
import com.peswa.leavemanager.enums.UserStatus;
import com.peswa.leavemanager.model.Role;
import com.peswa.leavemanager.model.User;
import com.peswa.leavemanager.model.UserGroup;
import com.peswa.leavemanager.model.UserGrouping;
import com.peswa.leavemanager.repository.RoleRepository;
import com.peswa.leavemanager.repository.UserRepository;
import com.peswa.leavemanager.security.JwtUtils;
import com.peswa.leavemanager.security.UserDetailsImpl;
import com.peswa.leavemanager.service.SequenceGeneratorService;
import com.peswa.leavemanager.service.UserGroupService;
import com.peswa.leavemanager.service.UserGroupingService;
import com.peswa.leavemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtTokenProvider;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    UserGroupingService userGroupingService;


    public LoginResponse loginUsers(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        LoginResponse loginResponse = null;
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userDetails != null) {
            String jwt = jwtTokenProvider.generateAccessToken(authentication);
            UserResponse userResponse = getUserResponseById(userDetails.getId());
           return new LoginResponse(jwt, "Bearer",userResponse);

        }
        return loginResponse;
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public UserResponse addUser(UserRequest userRequest) {
        return getUserResponse(addUserR(userRequest));
    }

    public User addUserR(UserRequest userRequest){
        Role role = roleRepository.findByName(userRequest.getRoleName()).orElse(null);
        User user = new User(sequenceGeneratorService.generateSequence(role.SEQUENCE_NAME),
                userRequest.getUsername(),
                encoder.encode(userRequest.getPassword()));
        user.setStatus(UserStatus.PENDING);
        user.setCreatedAt(new Date());
        user.setRoles(Collections.singleton(role));
       return userRepository.save(user);
    }

    public User addAdmin(UserRequest userRequest){
        Role role = roleRepository.findByName(userRequest.getRoleName()).orElse(null);
        User user = new User(sequenceGeneratorService.generateSequence(role.SEQUENCE_NAME),
                userRequest.getUsername(),
                encoder.encode(userRequest.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(new Date());
        user.setRoles(Collections.singleton(role));
        return userRepository.save(user);
    }

    public UserResponse resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        user.setPassword(encoder.encode(request.getNewPassword()));
        user.setStatus(UserStatus.ACTIVE);
        User result = userRepository.save(user);
        return getUserResponse(result);
    }


    public UserResponse getUserResponseById(Long userId){
        UserResponse userResponse = null;
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            userResponse = getUserResponse(user);
        }
        return userResponse;
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElse(null);
    }

    public UserResponse getUserResponse(User result){
        if(result != null) {
          return new UserResponse(
                    result.getId(),
                    result.getUsername(),
                    result.getStatus(),
                    getRole(result.getRoles()));
        }
        return null;
    }

    private String[] getRole(Set<Role> role){


        String[] roleName = new String[role.size()];
        List<Role> roleList = new ArrayList<>(role);
        for(int i = 0; i < role.size(); i++){
            roleName[i] = roleList.get(i).getName().name();
        }
        return roleName;
    }

    public UserGroupResponse makeUserASupervisor(UserGroupRequest userGroupRequest, long userid){
        User user = getUserById(userid);
         UserGroup userGroup = userGroupService.updateUserGroup(userGroupRequest,user);
        return getUserGroupResponse(userGroup);
    }

    public UserGroupResponse getUserGroupResponse(UserGroup userGroup){
        return new UserGroupResponse(
                userGroup.getId(),
                userGroup.getGroupName(),
                userGroup.getFunction(),
                getUserResponse(userGroup.getSupervisor())
        );
    }

    public List<UserGroupResponse> getAllUserGroup(){
       List<UserGroup>  userGroupList = userGroupService.getAllUserGroup();
        List<UserGroupResponse> userGroupResponses = new ArrayList<>();
       for(UserGroup userGroup:userGroupList){
           userGroupResponses.add(getUserGroupResponse(userGroup));
       }
       return userGroupResponses;
    }


    public UserGroupingResponse addUserToAGroup(String groupName, long userid){
        User user = getUserById(userid);
       UserGrouping userGrouping =  userGroupingService.addUserToAGroup(groupName,user);
       return new UserGroupingResponse(
                userGrouping.getId(),
                getUserResponse(user),
               getUserGroupResponse(userGrouping.getUserGroup())
        );
    }


}
