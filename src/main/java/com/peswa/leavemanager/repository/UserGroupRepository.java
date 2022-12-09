package com.peswa.leavemanager.repository;

import com.peswa.leavemanager.model.User;
import com.peswa.leavemanager.model.UserGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserGroupRepository extends MongoRepository<UserGroup, String> {
    UserGroup findByGroupName(String groupName);

    UserGroup findBySupervisor(User supervisor);
}
