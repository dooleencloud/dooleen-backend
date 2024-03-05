package com.dooleen.service.batch.test;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTask {

    @Scheduled(fixedRate = 3000)
    public void scheduledTask1() {
        System.out.println("Task executed at " + LocalDateTime.now());
    }
}