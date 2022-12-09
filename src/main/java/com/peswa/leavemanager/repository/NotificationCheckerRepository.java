package com.peswa.leavemanager.repository;

import com.peswa.leavemanager.model.NotificationChecker;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.Optional;

public interface NotificationCheckerRepository extends MongoRepository<NotificationChecker, String> {

    Optional<NotificationChecker> findByLeavePlanIdAndSentDate(long leavePlanId, String sentDate);

}
