package com.peswa.leavemanager.email;


import lombok.Data;

import java.util.Map;

@Data
public class Email {
    String to;
    String cc;
    String from;
    String subject;
    String text;
    String template;

    Map<String, Object> properties;
}