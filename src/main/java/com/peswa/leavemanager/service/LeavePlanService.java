package com.peswa.leavemanager.service;


import com.peswa.leavemanager.dto.request.LeaveApprovalRequest;
import com.peswa.leavemanager.dto.request.LeavePlanRequest;
import com.peswa.leavemanager.dto.response.LeavePlanResponse;

import java.util.List;

public interface LeavePlanService {

    LeavePlanResponse addLeavePlan(LeavePlanRequest request);

    LeavePlanResponse updateLeavePlan(LeavePlanRequest request,long leavePlanId);

    LeavePlanResponse approveOrRejectLeavePlan(LeaveApprovalRequest request);

    List<LeavePlanResponse> getLeavePlansBySupervisor(long supervisorUserId);

    List<LeavePlanResponse> getLeavePlansByUser(long userId);

    List<LeavePlanResponse> getAllPendingLeavePlan();
}
