package ru.stepup.spring.coins.core.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulersConfig {
    private static final Logger logger = LoggerFactory.getLogger(SchedulersConfig.class.getName());

    @Scheduled(cron = "${schedulers.scan-task}")
    public void scanTask() {
        logger.info("scan task executed...");
    }
}
