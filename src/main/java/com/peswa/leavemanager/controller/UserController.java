package com.peswa.leavemanager.controller;

import com.peswa.leavemanager.dto.request.EmployeeRequest;
import com.peswa.leavemanager.dto.request.UserGroupRequest;
import com.peswa.leavemanager.dto.response.BaseResponse;
import com.peswa.leavemanager.dto.response.EmployeeResponse;
import com.peswa.leavemanager.dto.response.UserGroupResponse;
import com.peswa.leavemanager.dto.response.UserGroupingResponse;
import com.peswa.leavemanager.model.UserGroup;
import com.peswa.leavemanager.model.UserGrouping;
import com.peswa.leavemanager.service.UserGroupService;
import com.peswa.leavemanager.service.UserGroupingService;
import com.peswa.leavemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    UserGroupingService userGroupingService;


    @PostMapping("/addUserGroup")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> addUserGroup(@Valid @RequestBody UserGroupRequest request) {

        UserGroup response = userGroupService.addUserGroup(request);
        if(response != null){
            return ResponseEntity.ok(new BaseResponse(true, "User group created successfully", response));
        } else {
            return new ResponseEntity<>(new BaseResponse(false, "Failed to create user group", null),
                    HttpStatus.OK);
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/getUserGroups")
    public ResponseEntity<BaseResponse<List<UserGroupResponse>>> getUserGroups() {

        List<UserGroupResponse> resp = userService.getAllUserGroup();
        if(resp != null){
            return ResponseEntity.ok(new BaseResponse(true, "User groups retrieved successfully", resp));
        } else {
            return new ResponseEntity<>(new BaseResponse(false, "User group not found.", null),
                    HttpStatus.OK);
        }
    }


    @PutMapping("/makeUserASupervisor/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> makeUserASupervisor(@Valid @RequestBody UserGroupRequest request,
                                                 @PathVariable("userId") Long userId) {

        UserGroupResponse response = userService.makeUserASupervisor(request,userId);
        if(response != null){
            return ResponseEntity.ok(new BaseResponse(true, "User group created successfully", response));
        } else {
            return new ResponseEntity<>(new BaseResponse(false, "Failed to create user group", null),
                    HttpStatus.OK);
        }
    }


    @PostMapping("/addUserToAGroup/group/{groupName}/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> addUserToAGroup(@PathVariable("groupName") String groupName,
                                          @PathVariable("userId") Long userId) {

        UserGroupingResponse response = userService.addUserToAGroup(groupName,userId);
        if(response != null){
            return ResponseEntity.ok(new BaseResponse(true, "User added successfully to user group: "+groupName, response));
        } else {
            return new ResponseEntity<>(new BaseResponse(false, "Failed to add user to group", null),
                    HttpStatus.OK);
        }
    }


}
