package com.peswa.leavemanager.dto.request;

import com.peswa.leavemanager.enums.LeaveApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApprovalRequest {
    private long leavePlanId;
    private long supervisorUserId;
    private LeaveApprovalStatus status;
}
