package com.peswa.leavemanager.controller;

import com.peswa.leavemanager.dto.request.LeaveApprovalRequest;
import com.peswa.leavemanager.dto.request.LeavePlanRequest;
import com.peswa.leavemanager.dto.response.BaseResponse;
import com.peswa.leavemanager.dto.response.LeavePlanResponse;
import com.peswa.leavemanager.service.LeavePlanService;
import com.peswa.leavemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/leavePlan")
public class LeavePlanController {

        @Autowired
        LeavePlanService leavePlanService;

        @Autowired
        UserService userService;

    @PostMapping("/")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addALeavePlan(@Valid @RequestBody LeavePlanRequest request) {
        System.out.println("Got here 1");
            LeavePlanResponse leavePlanResponse = leavePlanService.addLeavePlan(request);
            if(leavePlanResponse != null){
                return ResponseEntity.ok(new BaseResponse(true, "Leave plan created successfully", leavePlanResponse));
            } else {
                return new ResponseEntity<>(new BaseResponse(false, "Failed to create leave plan.", null),
                        HttpStatus.OK);
            }
    }

    @PutMapping("/update/{leavePlanId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateLeavePlan(@Valid @RequestBody LeavePlanRequest request,
                                           @PathVariable("leavePlanId") Long leavePlanId) {
        LeavePlanResponse leavePlanResponse = leavePlanService.updateLeavePlan(request,leavePlanId);
        if(leavePlanResponse != null){
            return ResponseEntity.ok(new BaseResponse(true, "Leave plan updated successfully", leavePlanResponse));
        } else {
            return new ResponseEntity<>(new BaseResponse(false, "Failed to update leave plan.", null),
                    HttpStatus.OK);
        }
    }


    @PostMapping("/approveOrReject")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> approveOrRejectLeave(@Valid @RequestBody LeaveApprovalRequest request) {
        LeavePlanResponse leavePlanResponse = leavePlanService.approveOrRejectLeavePlan(request);
        if(leavePlanResponse != null){
            return ResponseEntity.ok(new BaseResponse(true, "Leave plan treated successfully", leavePlanResponse));
        } else {
            return new ResponseEntity<>(new BaseResponse(false, "Failed to treat leave plan.", null),
                    HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getListOfLeavePlanAsSupervisor/{supervisorUserId}")
    public ResponseEntity<BaseResponse<List<LeavePlanResponse>>> getListOfLeavePlanAsSupervisor(@PathVariable("supervisorUserId") Long supervisorUserId) {

        List<LeavePlanResponse> leavePlanResponses = leavePlanService.getLeavePlansBySupervisor(supervisorUserId);
        if(leavePlanResponses != null){
            return ResponseEntity.ok(new BaseResponse(true, "Leave plans retrieved successfully", leavePlanResponses));
        } else {
            return new ResponseEntity<>(new BaseResponse(false, "Leave plan not found.", null),
                    HttpStatus.OK);
        }
    }


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getListOfLeavePlanAsUser/{userId}")
    public ResponseEntity<BaseResponse<List<LeavePlanResponse>>> getListOfLeavePlanAsUser(@PathVariable("userId") Long userId) {

        List<LeavePlanResponse> leavePlanResponses = leavePlanService.getLeavePlansByUser(userId);
        if(leavePlanResponses != null){
            return ResponseEntity.ok(new BaseResponse(true, "Leave plans retrieved successfully", leavePlanResponses));
        } else {
            return new ResponseEntity<>(new BaseResponse(false, "Leave plan not found.", null),
                    HttpStatus.OK);
        }
    }


}