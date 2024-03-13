package ru.stepup.spring.coins.core.services;

import org.springframework.stereotype.Service;
import ru.stepup.spring.coins.core.api.ExecuteCoinsRequest;
import ru.stepup.spring.coins.core.api.ExecuteCoinsResponse;
import ru.stepup.spring.coins.core.integrations.ExecutorIntegration;
import ru.stepup.spring.coins.core.integrations.dtos.CoinsExecuteDtoRs;

@Service
public class ExecutorService {
    private final ExecutorIntegration executorIntegration;

    public ExecutorService(ExecutorIntegration executorIntegration) {
        this.executorIntegration = executorIntegration;
    }

    public ExecuteCoinsResponse execute(ExecuteCoinsRequest request) {
        CoinsExecuteDtoRs response = executorIntegration.execute(request);
        return new ExecuteCoinsResponse(
                response.id(),
                response.status()
        );
    }
}
