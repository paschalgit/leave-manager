package com.peswa.leavemanager.email;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;


    @Value("${app.mail.noreply}")
    private String noReply;

    @Async
    public void sendWelcomeEmail(String emailAddress,String recipientName,String username,String password){
        Email email = new Email();
        email.setTo(emailAddress);
        email.setFrom(noReply);
        email.setSubject("Welcome to Peswa");
        email.setTemplate("welcome-email.html");
        Map<String, Object> properties = new HashMap<>();
        properties.put("recipientName", recipientName);
        properties.put("username", username);
        properties.put("password", password);
        email.setProperties(properties);
        try {
            sendHtmlMessage(email);
        }catch (Exception ex){
            System.out.println("Error sending email "+ex.toString());
        }
    }

    @Async
    public void sendLeavePlanCreationEmailToSupervisor(String emailAddress,String recipientName){
        Email email = new Email();
        email.setTo(emailAddress);
        email.setFrom(noReply);
        email.setSubject("A new Leave request is created");
        email.setTemplate("leave-plan-creation.html");
        Map<String, Object> properties = new HashMap<>();
        properties.put("recipientName", recipientName);
        email.setProperties(properties);
        try {
            sendHtmlMessage(email);
        }catch (Exception ex){
            System.out.println("Error sending email "+ex.toString());
        }
    }

    @Async
    public void sendLeavePlanReminderEmailToSupervisor(String emailAddress,String recipientName){
        Email email = new Email();
        email.setTo(emailAddress);
        email.setFrom(noReply);
        email.setSubject("Pending leave request");
        email.setTemplate("leave-plan-reminder.html");
        Map<String, Object> properties = new HashMap<>();
        properties.put("recipientName", recipientName);
        email.setProperties(properties);
        try {
            sendHtmlMessage(email);
        }catch (Exception ex){
            System.out.println("Error sending email "+ex.toString());
        }
    }

    @Async
    public void sendLeavePlanApprovalEmailToRequesterAndSupervisor(String requesterEmail,
                                                                   String recipientName,
                                                                   String supervisorEmail){
        Email email = new Email();
        email.setTo(requesterEmail);
        email.setCc(supervisorEmail);
        email.setFrom(noReply);
        email.setSubject("Your leave plan is treated");
        email.setTemplate("treated-leave-plan.html");
        Map<String, Object> properties = new HashMap<>();
        properties.put("recipientName", recipientName);
        email.setProperties(properties);
        try {
            sendHtmlMessage(email);
        }catch (Exception ex){
            System.out.println("Error sending email "+ex.toString());
        }
    }



    @Async
    public void sendHtmlMessage(Email email) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(email.getProperties());
        helper.setFrom(email.getFrom());
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());
        String html = templateEngine.process(email.getTemplate(), context);
        helper.setText(html, true);

        log.info("Sending email: {} with html body: {}", email, html);
        emailSender.send(message);
    }


}