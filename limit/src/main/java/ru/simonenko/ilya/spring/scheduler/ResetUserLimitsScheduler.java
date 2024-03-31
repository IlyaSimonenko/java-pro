package ru.simonenko.ilya.spring.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.simonenko.ilya.spring.service.UserLimitService;

@Component
public class ResetUserLimitsScheduler {
    private final UserLimitService userLimitService;

    @Autowired
    public ResetUserLimitsScheduler(UserLimitService userLimitService) {
        this.userLimitService = userLimitService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void resetUserLimits() {
        userLimitService.updateLimits();
    }

}
