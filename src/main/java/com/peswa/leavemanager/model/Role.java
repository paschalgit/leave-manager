package com.peswa.leavemanager.model;

import com.peswa.leavemanager.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "roles")
public class Role {
    @Id
    private long id;
    private RoleEnum name;

    @Transient
    public static final String SEQUENCE_NAME = "role_sequence";

    public Role(RoleEnum name) {
        this.name = name;
    }

}
