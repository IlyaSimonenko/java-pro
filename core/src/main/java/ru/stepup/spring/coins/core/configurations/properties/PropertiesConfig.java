package ru.stepup.spring.coins.core.configurations.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        CoreProperties.class,
        ExecutorProperties.class
})
public class PropertiesConfig {
}
