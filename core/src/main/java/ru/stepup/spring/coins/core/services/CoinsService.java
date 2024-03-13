package ru.stepup.spring.coins.core.services;

import org.springframework.stereotype.Service;
import ru.stepup.spring.coins.core.api.ExecuteCoinsRequest;
import ru.stepup.spring.coins.core.api.ExecuteCoinsResponse;
import ru.stepup.spring.coins.core.exceptions.BadRequestException;
import ru.stepup.spring.coins.core.configurations.properties.CoreProperties;

@Service
public class CoinsService {
    private final CoreProperties coreProperties;
    private final ExecutorService executorService;

    public CoinsService(CoreProperties coreProperties, ExecutorService executorService) {
        this.coreProperties = coreProperties;
        this.executorService = executorService;
    }

    public ExecuteCoinsResponse execute(ExecuteCoinsRequest request) {
        if (coreProperties.getNumbersBlockingEnabled()) {
            if (coreProperties.getBlockedNumbers().contains(request.number())) {
                throw new BadRequestException("Указан заблокированный номер кошелька", "BLOCKED_ACCOUNT_NUMBER");
            }
        }
        ExecuteCoinsResponse response = executorService.execute(request);
        return response;
    }
}
