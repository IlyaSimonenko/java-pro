package ru.simonenko.ilya.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simonenko.ilya.spring.dto.UserLimit;
import ru.simonenko.ilya.spring.service.UserLimitService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/limit")
public class UserLimitController {

    private final UserLimitService userLimitService;

    @Autowired
    public UserLimitController(final UserLimitService userLimitService) {
        this.userLimitService = userLimitService;
    }

    @PostMapping("/reduce")
    public ResponseEntity<UserLimit> reduceLimit(@RequestHeader("userId") Long userId, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(userLimitService.reduceLimit(userId, amount));
    }

    @PostMapping("/restore")
    public ResponseEntity<UserLimit> restoreLimit(@RequestHeader("userId") Long userId, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(userLimitService.restoreLimit(userId, amount));
    }

    @PutMapping("/")
    public ResponseEntity<UserLimit> updateLimit(@RequestHeader("userId") Long userId, @RequestParam BigDecimal limit) {
        return ResponseEntity.ok(userLimitService.updateLimit(userId, limit));
    }

}
