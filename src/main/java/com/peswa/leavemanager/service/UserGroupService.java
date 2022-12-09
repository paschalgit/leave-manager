package com.peswa.leavemanager.service;


import com.peswa.leavemanager.dto.request.UserGroupRequest;
import com.peswa.leavemanager.model.User;
import com.peswa.leavemanager.model.UserGroup;

import java.util.List;

public interface UserGroupService {
    UserGroup addUserGroup(UserGroupRequest userGroupRequest);

    UserGroup updateUserGroup(UserGroupRequest userGroupRequest, User supervisor);

    List<UserGroup> getAllUserGroup();
}
