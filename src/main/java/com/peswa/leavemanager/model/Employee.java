package com.peswa.leavemanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "employee")
public class Employee {

    @Id
    private long id;

    private String firstName;
    private String lastName;

    @NotBlank
    @Indexed(unique = true)
    @Email
    private String personalEmail;

    @NotBlank
    @Indexed(unique = true)
    @Email
    private String officialEmail;
    private Date dateOfBirth;
    private Date employmentStartDate;

    private Date employmentEndDate;

    @DBRef
    //@DocumentReference(lazy=true)
    private User user;


    @Transient
    public static final String SEQUENCE_NAME = "employee_sequence";

    public Employee(long id, String firstName, String lastName,String personalEmail, String officialEmail, Date dateOfBirth, Date employmentStartDate, User user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalEmail = personalEmail;
        this.officialEmail = officialEmail;
        this.dateOfBirth = dateOfBirth;
        this.employmentStartDate = employmentStartDate;
        this.user = user;
    }
}
