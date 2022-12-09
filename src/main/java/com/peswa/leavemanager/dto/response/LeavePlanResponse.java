package com.peswa.leavemanager.dto.response;

import com.peswa.leavemanager.enums.LeaveApprovalStatus;
import com.peswa.leavemanager.enums.LeaveTypeEnum;
import com.peswa.leavemanager.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeavePlanResponse {
    private long leavePlanId;
    private long requesterUserId;
    private LeaveTypeEnum leaveType; //vacation,study leave,sick leave,short leave
    private Date startDate;
    private Date endDate;
    private Date requestDate;
    private Date approveDate;
    private long approvedById;
    private LeaveApprovalStatus approvalStatus; //pending, approved, rejected
    private long handedOverToId;
}
