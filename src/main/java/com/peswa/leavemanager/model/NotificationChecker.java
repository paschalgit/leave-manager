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

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notification_checker")
public class NotificationChecker {

    @Id
    private long id;
    private long leavePlanId;
    private String sentDate;

    @Transient
    public static final String SEQUENCE_NAME = "notification_checker_sequence";

}

