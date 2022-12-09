package com.peswa.leavemanager.schedular;

import com.peswa.leavemanager.dto.request.LeaveApprovalRequest;
import com.peswa.leavemanager.dto.response.LeavePlanResponse;
import com.peswa.leavemanager.email.EmailSenderService;
import com.peswa.leavemanager.enums.LeaveApprovalStatus;
import com.peswa.leavemanager.model.Employee;
import com.peswa.leavemanager.model.NotificationChecker;
import com.peswa.leavemanager.repository.NotificationCheckerRepository;
import com.peswa.leavemanager.service.LeavePlanService;
import com.peswa.leavemanager.service.SequenceGeneratorService;
import com.peswa.leavemanager.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MonitorLeavePlans {

    @Autowired
    LeavePlanService leavePlanService;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    NotificationCheckerRepository notificationCheckerRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Value("${peswa.app.pending.leave.max_time_in_hours}")
    private String max_time_in_hours;

    public void checkForLeaveRequests(){
        List<LeavePlanResponse> leavePlans = leavePlanService.getAllPendingLeavePlan();
        if(leavePlans!=null){
            int maxTime = Integer.parseInt(max_time_in_hours);
            System.out.println("Max time "+maxTime);
            for(LeavePlanResponse lv:leavePlans){
               long totalMins = AppUtil.getDateDifferenceInMins(lv.getRequestDate());
               long totalHours = totalMins/60;
               if(totalHours<maxTime){
                    //Auto approve the leave
                   leavePlanService.approveOrRejectLeavePlan(new LeaveApprovalRequest(
                           lv.getLeavePlanId(),
                           lv.getApprovedById(),
                           LeaveApprovalStatus.APPROVED
                   ));
               }else{
                   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                   //check if a leave plan reminder has been sent for that day
                   NotificationChecker checkerL = notificationCheckerRepository.
                           findByLeavePlanIdAndSentDate(lv.getLeavePlanId(),sdf.format(new Date())).orElse(null);
                   if(checkerL==null) {
                       //Send a reminder email
                       emailSenderService.sendLeavePlanReminderEmailToSupervisor("", "");
                       //Add to the notification checker
                       NotificationChecker checker = new NotificationChecker();
                       checker.setId(sequenceGeneratorService.generateSequence(NotificationChecker.SEQUENCE_NAME));
                       checker.setLeavePlanId(lv.getLeavePlanId());
                       checker.setSentDate(sdf.format(new Date()));
                       notificationCheckerRepository.save(checker);
                   }
               }

            }

        }

    }
}
