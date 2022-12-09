package com.peswa.leavemanager.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupResponse {
    private long id;
    private String userGroupName;
    private String userGroupFunction;
    private UserResponse supervisor;
}
