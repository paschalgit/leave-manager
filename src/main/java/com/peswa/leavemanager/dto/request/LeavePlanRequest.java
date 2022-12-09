package com.peswa.leavemanager.dto.request;

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
public class LeavePlanRequest {

    private long userId;
    private LeaveTypeEnum leaveType; //vacation,study leave,sick leave,short leave
    private Date startDate;
    private Date endDate;
    private long handedOverToId;
}
