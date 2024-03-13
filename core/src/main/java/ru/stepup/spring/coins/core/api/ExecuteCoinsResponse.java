package ru.stepup.spring.coins.core.api;

public record ExecuteCoinsResponse(
        String coinsTransactionId,
        String status
) {
}
