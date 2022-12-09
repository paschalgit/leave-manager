/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peswa.leavemanager.service.impl;

import com.peswa.leavemanager.dto.request.RoleRequest;
import com.peswa.leavemanager.dto.response.RoleResponse;
import com.peswa.leavemanager.enums.RoleEnum;
import com.peswa.leavemanager.model.Role;
import com.peswa.leavemanager.repository.RoleRepository;
import com.peswa.leavemanager.service.RoleService;
import com.peswa.leavemanager.service.SequenceGeneratorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    
     @Autowired
    private RoleRepository roleRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    public boolean existsByName(RoleEnum name) {
        return roleRepository.existsByName(name).orElse(false);
    }

    public RoleResponse addRole(RoleRequest roleRequest) {
        RoleResponse roleResponse = null;
        Role role = new Role(roleRequest.getName());
          role.setId(sequenceGeneratorService.generateSequence(role.SEQUENCE_NAME));
        if (!existsByName(roleRequest.getName())) {
            Role result = roleRepository.save(role);
            if (result != null) {
                roleResponse = new RoleResponse();
                BeanUtils.copyProperties(role, roleResponse);
            }
        }
        return roleResponse;
    }

    public List<RoleResponse> getRoles() {
        List<RoleResponse> roleResponses = new ArrayList<>();
        List<Role> roleList = roleRepository.findAll();

        roleList.stream().forEach((role) -> {
            RoleResponse roleResponse = new RoleResponse();
            BeanUtils.copyProperties(role, roleResponse);
            roleResponses.add(roleResponse);
        });
        return roleResponses;
    }

    public Role getRole(RoleEnum name) {
        return roleRepository.findByName(name).orElse(null);
    }

    public RoleResponse getRole(Long roleId) {
        Role role = roleRepository.findById(roleId).orElse(null);
        RoleResponse roleResponse = null;
        if (role != null) {
            roleResponse =  new RoleResponse();
            BeanUtils.copyProperties(role, roleResponse);
        }
        return roleResponse;
    }

    public RoleResponse updateRole(Long roleId, RoleRequest roleRequest) {
        RoleResponse roleResponse = null;
        Role role = new Role();
        role.setId(roleId);
        role.setName(roleRequest.getName());
        Role result = roleRepository.save(role);
        if (result != null) {
            roleResponse = new RoleResponse();
            BeanUtils.copyProperties(result, roleResponse);
        }
        return roleResponse;
    }

    public void deleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }
    
}
