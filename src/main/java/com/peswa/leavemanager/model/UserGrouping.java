package com.peswa.leavemanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_grouping")
public class UserGrouping {

    @Id
    private long id;

    @DBRef
    //@DocumentReference(lazy=true)
    private User user;

    @DBRef
    private UserGroup userGroup;

    @Transient
    public static final String SEQUENCE_NAME = "user_grouping_sequence";

}
