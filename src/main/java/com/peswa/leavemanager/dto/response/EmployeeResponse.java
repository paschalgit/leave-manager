package com.peswa.leavemanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private long id;
    private String firstName;
    private String lastName;
    private String personalEmail;
    private String officialEmail;
    private Date dateOfBirth;
    private Date employmentStartDate;
    private Date employmentEndDate;
    private UserResponse userResponse;

}
