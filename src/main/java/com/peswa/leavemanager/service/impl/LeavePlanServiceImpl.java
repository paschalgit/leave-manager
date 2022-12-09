package com.peswa.leavemanager.service.impl;

import com.peswa.leavemanager.dto.request.*;
import com.peswa.leavemanager.dto.response.*;
import com.peswa.leavemanager.email.EmailSenderService;
import com.peswa.leavemanager.enums.LeaveApprovalStatus;
import com.peswa.leavemanager.enums.UserStatus;
import com.peswa.leavemanager.model.*;
import com.peswa.leavemanager.repository.LeavePlanRepository;
import com.peswa.leavemanager.repository.RoleRepository;
import com.peswa.leavemanager.repository.UserRepository;
import com.peswa.leavemanager.security.JwtUtils;
import com.peswa.leavemanager.security.UserDetailsImpl;
import com.peswa.leavemanager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeavePlanServiceImpl implements LeavePlanService {


    @Autowired
    LeavePlanRepository leavePlanRepository;

    @Autowired
    UserService userService;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    UserGroupingService userGroupingService;

    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmailSenderService emailSenderService;

    public LeavePlanResponse addLeavePlan(LeavePlanRequest request){
        if(request!=null){
            System.out.println("Got here 2");
            User user = userService.getUserById(request.getUserId());
            //get user's group
           UserGrouping userGrouping = userGroupingService.getUserGroupingByUserId(user);
           UserGroup userGroup = userGrouping.getUserGroup();
            //get user's supervisor

            LeavePlan leavePlan = new LeavePlan(sequenceGeneratorService.generateSequence(LeavePlan.SEQUENCE_NAME),
                    user,
                    request.getLeaveType(),
                    request.getStartDate(),
                    request.getEndDate(),
                    new Date(),
                    null,
                    userGroup.getSupervisor().getId(),
                    LeaveApprovalStatus.PENDING,
                    request.getHandedOverToId()
            );
            LeavePlan result = leavePlanRepository.save(leavePlan);
            if(result!=null) {
                //Send an email notification to the supervisor
                EmployeeResponse employee = employeeService.getEmployeeByUserId(userGroup.getSupervisor().getId());
                if (employee != null) {
                    emailSenderService.sendLeavePlanCreationEmailToSupervisor(employee.getOfficialEmail(),employee.getFirstName());
                }
            }
            return  getLeavePlanResponse(result);
        }
        return  null;
    }

    public LeavePlanResponse updateLeavePlan(LeavePlanRequest request,long leavePlanId){
        LeavePlan leavePlan = leavePlanRepository.findById(leavePlanId);
                leavePlan.setLeaveType(request.getLeaveType());
                leavePlan.setStartDate(request.getStartDate());
                leavePlan.setEndDate(request.getEndDate());
                leavePlan.setHandedOverToId(request.getHandedOverToId());
            LeavePlan result = leavePlanRepository.save(leavePlan);
            return  getLeavePlanResponse(result);
    }

    public LeavePlanResponse approveOrRejectLeavePlan(LeaveApprovalRequest request){
        LeavePlan leavePlan = leavePlanRepository.findById(request.getLeavePlanId());
        leavePlan.setApprovedById(request.getSupervisorUserId());
        leavePlan.setApprovalStatus(request.getStatus());
        leavePlan.setApproveDate(new Date());
        LeavePlan result = leavePlanRepository.save(leavePlan);
        if(result!=null) {
            //Send an email notification to the requester and supervisor
                emailSenderService.sendLeavePlanApprovalEmailToRequesterAndSupervisor("","","");
        }
        return  getLeavePlanResponse(result);
    }


    public LeavePlanResponse getLeavePlanResponse(LeavePlan result){
        if(result != null) {
            return new LeavePlanResponse(
                    result.getId(),
                    result.getUser().getId(),
                    result.getLeaveType(),
                    result.getStartDate(),
                    result.getEndDate(),
                    result.getRequestDate(),
                    result.getApproveDate(),
                    result.getApprovedById(),
                    result.getApprovalStatus(),
                    result.getHandedOverToId()
            );
        }
        return null;
    }


    public List<LeavePlanResponse> getLeavePlansBySupervisor(long supervisorUserId){
       List<LeavePlan>  leavePlans = leavePlanRepository.findByApprovedById(supervisorUserId);
        List<LeavePlanResponse> leavePlanResponses = new ArrayList<>();
       for(LeavePlan leavePlan:leavePlans){
           leavePlanResponses.add(getLeavePlanResponse(leavePlan));
       }
       return leavePlanResponses;
    }

    public List<LeavePlanResponse> getLeavePlansByUser(long userId){
        User user = userService.getUserById(userId);
        if(user!=null) {
            List<LeavePlan> leavePlans = leavePlanRepository.findByUser(user);
            List<LeavePlanResponse> leavePlanResponses = new ArrayList<>();
            for (LeavePlan leavePlan : leavePlans) {
                leavePlanResponses.add(getLeavePlanResponse(leavePlan));
            }
            return leavePlanResponses;
        }
        return null;
    }

    public List<LeavePlanResponse> getAllPendingLeavePlan(){
            List<LeavePlan> leavePlans = leavePlanRepository.findByApprovalStatus(LeaveApprovalStatus.PENDING);
            List<LeavePlanResponse> leavePlanResponses = new ArrayList<>();
            for (LeavePlan leavePlan : leavePlans) {
                leavePlanResponses.add(getLeavePlanResponse(leavePlan));
            }
            return leavePlanResponses;
    }

}
