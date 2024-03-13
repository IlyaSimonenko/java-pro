package ru.stepup.spring.coins.core.configurations.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Set;

@ConfigurationProperties(prefix = "core")
public class CoreProperties {
    private Boolean numbersBlockingEnabled;
    private Set<String> blockedNumbers;

    public Boolean getNumbersBlockingEnabled() {
        return numbersBlockingEnabled;
    }

    public Set<String> getBlockedNumbers() {
        return blockedNumbers;
    }

    @ConstructorBinding
    public CoreProperties(Boolean numbersBlockingEnabled, Set<String> blockedNumbers) {
        this.numbersBlockingEnabled = numbersBlockingEnabled;
        this.blockedNumbers = blockedNumbers;
    }
}
