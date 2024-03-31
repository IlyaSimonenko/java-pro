package ru.simonenko.ilya.spring.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class UserLimit {
    private Long userId;

    private BigDecimal dailyLimit;

    public UserLimit(Long userId, BigDecimal dailyLimit) {
        this.userId = userId;
        this.dailyLimit = dailyLimit;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(BigDecimal dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLimit userLimit = (UserLimit) o;
        return Objects.equals(userId, userLimit.userId) && Objects.equals(dailyLimit, userLimit.dailyLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, dailyLimit);
    }

    @Override
    public String toString() {
        return "UserLimit{" +
                "userId=" + userId +
                ", dailyLimit=" + dailyLimit +
                '}';
    }
}
