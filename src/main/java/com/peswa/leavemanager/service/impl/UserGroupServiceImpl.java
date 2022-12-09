package com.peswa.leavemanager.service.impl;

import com.peswa.leavemanager.dto.request.UserGroupRequest;
import com.peswa.leavemanager.model.Employee;
import com.peswa.leavemanager.model.User;
import com.peswa.leavemanager.model.UserGroup;
import com.peswa.leavemanager.repository.UserGroupRepository;
import com.peswa.leavemanager.service.SequenceGeneratorService;
import com.peswa.leavemanager.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;


    public UserGroup addUserGroup(UserGroupRequest userGroupRequest){
        UserGroup userGroup = new UserGroup(sequenceGeneratorService.generateSequence(UserGroup.SEQUENCE_NAME),
                userGroupRequest.getUserGroupName(),
                userGroupRequest.getUserGroupFunction());
       return userGroupRepository.save(userGroup);
    }


    public UserGroup updateUserGroup(UserGroupRequest userGroupRequest, User supervisor){
        UserGroup userGroup = userGroupRepository.findByGroupName(userGroupRequest.getUserGroupName());
        userGroup.setSupervisor(supervisor);
        return userGroupRepository.save(userGroup);
    }

    public List<UserGroup> getAllUserGroup(){
        List<UserGroup> userGroups = new ArrayList<UserGroup>();
        userGroupRepository.findAll().forEach(userGroups::add);
        return userGroups;
    }
}
