package ru.simonenko.ilya.spring.db;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class UserLimitEntity {

    @Id
    private Long userId;

    @Column(name = "daily_limit")
    private BigDecimal dailyLimit;

    public UserLimitEntity() {
    }

    public UserLimitEntity(Long userId, BigDecimal dailyLimit) {
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
        UserLimitEntity that = (UserLimitEntity) o;
        return Objects.equals(userId, that.userId) && Objects.equals(dailyLimit, that.dailyLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, dailyLimit);
    }

}
