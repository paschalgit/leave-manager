package com.peswa.leavemanager.repository;

import com.peswa.leavemanager.model.User;
import com.peswa.leavemanager.model.UserGroup;
import com.peswa.leavemanager.model.UserGrouping;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserGroupingRepository extends MongoRepository<UserGrouping, String> {
    List<UserGrouping> findByUserGroup(UserGroup userGroup);

    UserGrouping findByUser(User user);
}
