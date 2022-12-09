package com.peswa.leavemanager.repository;

import com.peswa.leavemanager.enums.LeaveApprovalStatus;
import com.peswa.leavemanager.model.LeavePlan;
import com.peswa.leavemanager.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LeavePlanRepository extends MongoRepository<LeavePlan, String> {
    LeavePlan  findById(long id);

    List<LeavePlan> findByUser(User requester);
    List<LeavePlan> findByApprovedById(long supervisorUserId);

    List<LeavePlan> findByApprovalStatus(LeaveApprovalStatus status);

}
