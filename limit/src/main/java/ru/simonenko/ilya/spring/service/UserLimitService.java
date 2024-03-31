package ru.simonenko.ilya.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simonenko.ilya.spring.db.UserLimitEntity;
import ru.simonenko.ilya.spring.dto.UserLimit;
import ru.simonenko.ilya.spring.exceptions.ResourceDoesNotMeetConditionsException;
import ru.simonenko.ilya.spring.repository.UserLimitRepository;

import java.math.BigDecimal;

@Service
public class UserLimitService {

    public static final String DAILY_LIMIT = "10000.00";
    private final UserLimitRepository userLimitRepository;

    @Autowired
    public UserLimitService(final UserLimitRepository userLimitRepository) {
        this.userLimitRepository = userLimitRepository;
    }

    @Transactional
    public UserLimit reduceLimit(Long userId, BigDecimal amount) {
        UserLimit userLimit = userLimitRepository.findById(userId)
                .map(this::convertToUserLimit)
                .orElseGet(() -> createLimitForUserThatDidNotExist(userId, amount));

        if (userLimit.getDailyLimit().compareTo(amount) >= 0) {
            userLimit.setDailyLimit(userLimit.getDailyLimit().subtract(amount));
            userLimitRepository.save(convertToUserLimitEntity(userLimit));
        } else {
            throw new ResourceDoesNotMeetConditionsException("Not enough daily limit for this user");
        }

        return userLimit;

    }

    public UserLimit restoreLimit(Long userId, BigDecimal amount) {
        UserLimit userLimit = userLimitRepository.findById(userId)
                .map(element -> new UserLimit(element.getUserId(), element.getDailyLimit().add(amount)))
                .orElseGet(() -> new UserLimit(userId, new BigDecimal(DAILY_LIMIT)));

        userLimitRepository.save(convertToUserLimitEntity(userLimit));

        return userLimit;

    }


    @Transactional
    public UserLimit updateLimit(Long userId, BigDecimal limit) {
        UserLimit userLimit = userLimitRepository.findById(userId)
                .map(element -> new UserLimit(element.getUserId(), limit))
                .orElseGet(() -> new UserLimit(userId, limit));

        userLimitRepository.save(convertToUserLimitEntity(userLimit));

        return userLimit;
    }

    @Transactional
    public void updateLimits() {
        var userLimits = userLimitRepository.findAll();

        userLimits.forEach(element -> element.setDailyLimit(new BigDecimal(DAILY_LIMIT)));

        userLimitRepository.saveAll(userLimits);

    }

    private UserLimit createLimitForUserThatDidNotExist(Long userId, BigDecimal amount) {
        BigDecimal dailyLimit = new BigDecimal(DAILY_LIMIT).subtract(amount);

        return new UserLimit(userId, dailyLimit);
    }

    private UserLimit convertToUserLimit(UserLimitEntity entity) {
        return new UserLimit(entity.getUserId(), entity.getDailyLimit());
    }

    private UserLimitEntity convertToUserLimitEntity(UserLimit userLimit) {
        return new UserLimitEntity(userLimit.getUserId(), userLimit.getDailyLimit());
    }

}
