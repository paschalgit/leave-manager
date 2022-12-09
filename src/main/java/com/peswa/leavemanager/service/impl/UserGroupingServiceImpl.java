package com.peswa.leavemanager.service.impl;

import com.peswa.leavemanager.dto.request.UserGroupRequest;
import com.peswa.leavemanager.model.User;
import com.peswa.leavemanager.model.UserGroup;
import com.peswa.leavemanager.model.UserGrouping;
import com.peswa.leavemanager.repository.UserGroupRepository;
import com.peswa.leavemanager.repository.UserGroupingRepository;
import com.peswa.leavemanager.service.SequenceGeneratorService;
import com.peswa.leavemanager.service.UserGroupService;
import com.peswa.leavemanager.service.UserGroupingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserGroupingServiceImpl implements UserGroupingService {

    @Autowired
    UserGroupingRepository userGroupingRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;


    public UserGrouping addUserToAGroup(String groupName,User user){
        UserGroup userGroup = userGroupRepository.findByGroupName(groupName);
        UserGrouping userGrouping = new UserGrouping(sequenceGeneratorService.generateSequence(UserGrouping.SEQUENCE_NAME),
                user, userGroup);
       return userGroupingRepository.save(userGrouping);
    }

    public UserGrouping getUserGroupingByUserId(User user){
        return userGroupingRepository.findByUser(user);
    }

}
