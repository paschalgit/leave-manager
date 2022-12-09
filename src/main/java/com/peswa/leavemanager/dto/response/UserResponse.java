package com.peswa.leavemanager.dto.response;

import com.peswa.leavemanager.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String username;
    private UserStatus status;
    private String[] roles;


}
