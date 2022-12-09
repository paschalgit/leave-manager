package com.peswa.leavemanager.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.peswa.leavemanager.dto.request.LoginRequest;
import com.peswa.leavemanager.dto.request.ResetPasswordRequest;
import com.peswa.leavemanager.dto.request.SignupRequest;
import com.peswa.leavemanager.dto.response.*;
import com.peswa.leavemanager.enums.RoleEnum;
import com.peswa.leavemanager.model.Role;
import com.peswa.leavemanager.model.User;
import com.peswa.leavemanager.repository.RoleRepository;
import com.peswa.leavemanager.repository.UserRepository;
import com.peswa.leavemanager.security.JwtUtils;
import com.peswa.leavemanager.security.UserDetailsImpl;
import com.peswa.leavemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> loginuser(@Valid @RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse = userService.loginUsers(loginRequest);
        if (loginResponse != null){
            return new ResponseEntity(loginResponse,
                    HttpStatus.OK);
        }else {
            return new ResponseEntity("Failed to login user",
                    HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {

        UserResponse response = userService.resetPassword(request);
        if(response != null){
            return ResponseEntity.ok(new BaseResponse(true, "Password reset done successfully: User's account is activated now.", response));
        } else {
            return new ResponseEntity<>(new BaseResponse(false, "Failed to reset password", null),
                    HttpStatus.OK);
        }
    }


}
