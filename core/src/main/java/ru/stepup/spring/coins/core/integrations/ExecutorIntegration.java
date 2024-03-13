package ru.stepup.spring.coins.core.integrations;

import ru.stepup.spring.coins.core.api.ExecuteCoinsRequest;
import ru.stepup.spring.coins.core.integrations.dtos.CoinsExecuteDtoRs;

public interface ExecutorIntegration {
    CoinsExecuteDtoRs execute(ExecuteCoinsRequest executeCoinsRequest);
}
