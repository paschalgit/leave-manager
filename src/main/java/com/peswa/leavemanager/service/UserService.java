package com.peswa.leavemanager.service;

import com.peswa.leavemanager.dto.request.LoginRequest;
import com.peswa.leavemanager.dto.request.ResetPasswordRequest;
import com.peswa.leavemanager.dto.request.UserGroupRequest;
import com.peswa.leavemanager.dto.request.UserRequest;
import com.peswa.leavemanager.dto.response.LoginResponse;
import com.peswa.leavemanager.dto.response.UserGroupResponse;
import com.peswa.leavemanager.dto.response.UserGroupingResponse;
import com.peswa.leavemanager.dto.response.UserResponse;
import com.peswa.leavemanager.model.User;
import com.peswa.leavemanager.model.UserGroup;
import com.peswa.leavemanager.model.UserGrouping;

import java.util.List;

public interface UserService {
    LoginResponse loginUsers(LoginRequest loginRequest);

    boolean existsByUsername(String username);

    User addUserR(UserRequest userRequest);
    UserResponse addUser(UserRequest userRequest);

    User addAdmin(UserRequest userRequest);

    UserResponse resetPassword(ResetPasswordRequest request);

    UserResponse getUserResponseById(Long userId);

    User getUserById(Long userId);

    UserGroupResponse makeUserASupervisor(UserGroupRequest userGroupRequest, long userid);

    List<UserGroupResponse> getAllUserGroup();

    UserGroupingResponse addUserToAGroup(String groupName, long userid);
}
