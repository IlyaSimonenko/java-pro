package ru.stepup.spring.coins.core.integrations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;
import ru.stepup.spring.coins.core.api.ExecuteCoinsRequest;
import ru.stepup.spring.coins.core.exceptions.IntegrationException;
import ru.stepup.spring.coins.core.integrations.dtos.CoinsExecuteDtoRq;
import ru.stepup.spring.coins.core.integrations.dtos.CoinsExecuteDtoRs;

public class ExecutorIntegrationRestClient implements ExecutorIntegration {
    private final RestClient restClient;

    private static final Logger logger = LoggerFactory.getLogger(ExecutorIntegrationRestClient.class.getName());

    public ExecutorIntegrationRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public CoinsExecuteDtoRs execute(ExecuteCoinsRequest executeCoinsRequest) {
        try {
            CoinsExecuteDtoRq coinsExecuteDtoRq = new CoinsExecuteDtoRq(
                    executeCoinsRequest.number(),
                    executeCoinsRequest.productId(),
                    executeCoinsRequest.productType()
            );

            CoinsExecuteDtoRs response = restClient.post()
                    .uri("/payments/execute")
                    // .body(coinsExecuteDtoRq)
                    .header("USERID", "12345678")
                    .header("Accept", "application/json")
                    .retrieve()
                    .body(CoinsExecuteDtoRs.class);
            logger.info("response: {}", response);
            return response;
        } catch (IntegrationException e) {
            logger.info("error body: {}", e.getIntegrationErrorDto());
            return null;
        }
    }
}
