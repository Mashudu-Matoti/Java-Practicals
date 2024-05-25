package com.organDonation;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BackgroundTaskService {

    @Async
    public void executeBackgroundTask() {
        // Simulate a long-running task
        try {
            Thread.sleep(5000); // 5 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Background task completed");
    }
}

