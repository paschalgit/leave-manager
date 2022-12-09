package com.peswa.leavemanager.service;

import com.peswa.leavemanager.dto.request.RoleRequest;
import com.peswa.leavemanager.dto.response.RoleResponse;
import com.peswa.leavemanager.enums.RoleEnum;
import com.peswa.leavemanager.model.Role;

import java.util.List;

public interface RoleService {

    boolean existsByName(RoleEnum name);

    RoleResponse addRole(RoleRequest roleRequest);

    List<RoleResponse> getRoles();

    Role getRole(RoleEnum name);

   RoleResponse getRole(Long roleId);

    RoleResponse updateRole(Long roleId, RoleRequest roleRequest);

    void deleteRole(Long roleId);
}
