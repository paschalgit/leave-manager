package com.peswa.leavemanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private  String token;
    private String type;
    private  String id;
    private String username;
    private List<String> roles;
}
