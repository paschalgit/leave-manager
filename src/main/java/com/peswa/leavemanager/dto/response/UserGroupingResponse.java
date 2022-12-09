package com.peswa.leavemanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupingResponse {
    private long id;
    private UserResponse userResponse;
    private UserGroupResponse userGroupResponse;
}
