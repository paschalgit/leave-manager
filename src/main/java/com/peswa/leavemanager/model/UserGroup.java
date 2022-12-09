package com.peswa.leavemanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_group")
public class UserGroup {

    @Id
    private long id;

    private String groupName;
    private String function;

    @DBRef
    private User supervisor;

    public UserGroup(long id, String groupName, String function) {
        this.id = id;
        this.groupName = groupName;
        this.function = function;
    }

    @Transient
    public static final String SEQUENCE_NAME = "user_group_sequence";

}
