package com.peswa.leavemanager.service;


import com.peswa.leavemanager.dto.request.UserGroupRequest;
import com.peswa.leavemanager.model.User;
import com.peswa.leavemanager.model.UserGroup;
import com.peswa.leavemanager.model.UserGrouping;

public interface UserGroupingService {
    UserGrouping addUserToAGroup(String groupName, User user);

    UserGrouping getUserGroupingByUserId(User user);
}
