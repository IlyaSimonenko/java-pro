package ru.stepup.spring.coins.core.api;

import ru.simonenko.ilya.spring.enums.ProductTypeEnum;

import java.math.BigDecimal;

public record ProductRequest(Long userId,
                             String accountNumber,
                             BigDecimal balance,
                             ProductTypeEnum productType) {
}
