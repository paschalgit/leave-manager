package com.peswa.leavemanager.schedular;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduleLeavePlanMonitoring {

    @Autowired
    MonitorLeavePlans monitorLeavePlans;

    @Scheduled(fixedDelayString = "${peswa.app.service.execution.millisec}",initialDelay = 18000)
    public void RetryFT(){
        log.info("Checking for new leave requests");
        monitorLeavePlans.checkForLeaveRequests();
    }

}
