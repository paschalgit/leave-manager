package com.peswa.leavemanager.model;

import com.peswa.leavemanager.enums.LeaveApprovalStatus;
import com.peswa.leavemanager.enums.LeaveTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "leave_plan")
public class LeavePlan {

    @Id
    private long id;

    //@DocumentReference(lazy=true)
    @DBRef
    private User user;
    private LeaveTypeEnum leaveType; //vacation,study leave,sick leave,short leave
    private Date startDate;
    private Date endDate;
    private Date requestDate;
    private Date approveDate;
    private long approvedById;
    private LeaveApprovalStatus approvalStatus; //pending, approved, rejected
    private long handedOverToId;


    @Transient
    public static final String SEQUENCE_NAME = "leave_plan_sequence";

}

//Assign a user to be a super visor
//Assign user to a  supervisor
//create password reset endpoint so user's account can be enabled
//create a leave plane
//Allow user's who has access to the supervisor role to see the request
