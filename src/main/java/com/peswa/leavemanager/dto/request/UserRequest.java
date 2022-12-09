package com.peswa.leavemanager.dto.request;

import com.peswa.leavemanager.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String username;
    private String password;
    private RoleEnum roleName;

}
