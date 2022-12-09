/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peswa.leavemanager.controller;

import com.peswa.leavemanager.dto.request.RoleRequest;
import com.peswa.leavemanager.dto.response.BaseResponse;
import com.peswa.leavemanager.dto.response.RoleResponse;
import com.peswa.leavemanager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    
    @Autowired
    private RoleService roleService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<BaseResponse> createRole(@Valid @RequestBody RoleRequest roleRequest) {
        if (roleService.existsByName(roleRequest.getName())) {
            return ResponseEntity.ok(new BaseResponse(false, "Role name already exist.",
                    null
            ));
        }
        RoleResponse roleResponse = roleService.addRole(roleRequest);
        if (roleResponse != null) {
            return ResponseEntity.ok(new BaseResponse(true, "Role created successfully.",
                    roleResponse
            ));
        } else {
            return ResponseEntity.ok(new BaseResponse(false, "Failed to create role.",
                    null
            ));
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<BaseResponse<List<RoleResponse>>> getRoles( HttpServletRequest request) {
        List<RoleResponse> roleResponses = roleService.getRoles();
        if (roleResponses != null) {
            return ResponseEntity.ok(new BaseResponse<>(true, "Role retrieved successfully", roleResponses));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(false, "Roles not found", null));
        }
    }

    @GetMapping("/{roleId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<BaseResponse<RoleResponse>> getRole(@PathVariable("roleId") Long roleId) {
        RoleResponse roleResponse = roleService.getRole(roleId);
        if (roleResponse != null) {
            return ResponseEntity.ok(new BaseResponse<>(true, "Role retrieved successful", roleResponse));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(false, "Role not found", null));
        }
    }

    @PutMapping("/{roleId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<BaseResponse> updateRole(@PathVariable("roleId") Long roleId,
            @Valid @RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = roleService.updateRole(roleId, roleRequest);
        if (roleResponse != null) {
            return ResponseEntity.ok(new BaseResponse<>(true, "Role updated successfully.",
                    roleResponse
            ));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(false, "Failed to update role", null));
        }
    }
    
    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<BaseResponse> deleteRole(@PathVariable("roleId") Long roleId){
        roleService.deleteRole(roleId);
        return ResponseEntity.ok(new BaseResponse<>(true, "Role deleted successfully.",
                null
        ));
    }
    
}
