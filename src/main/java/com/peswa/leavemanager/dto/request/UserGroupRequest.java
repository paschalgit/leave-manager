package com.peswa.leavemanager.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupRequest {
    private String userGroupName;
    private String userGroupFunction;
}
