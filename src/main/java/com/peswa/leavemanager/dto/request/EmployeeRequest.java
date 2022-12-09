package com.peswa.leavemanager.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String personalEmail;
    private Date employmentStartDate;
    private Date employmentEndDate;

}
