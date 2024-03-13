package ru.stepup.spring.coins.core.integrations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ru.stepup.spring.coins.core.api.ExecuteCoinsRequest;
import ru.stepup.spring.coins.core.exceptions.IntegrationException;
import ru.stepup.spring.coins.core.integrations.dtos.CoinsExecuteDtoRq;
import ru.stepup.spring.coins.core.integrations.dtos.CoinsExecuteDtoRs;

import java.util.Collections;

public class ExecutorIntegrationRestTemplate implements ExecutorIntegration {
    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ExecutorIntegrationRestTemplate.class.getName());

    public ExecutorIntegrationRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CoinsExecuteDtoRs execute(ExecuteCoinsRequest executeCoinsRequest) {
        try {
            CoinsExecuteDtoRq coinsExecuteDtoRq = new CoinsExecuteDtoRq(
                    executeCoinsRequest.number(),
                    executeCoinsRequest.productId(),
                    executeCoinsRequest.productType()
            );

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<CoinsExecuteDtoRq> request = new HttpEntity<>(
                    coinsExecuteDtoRq,
                    httpHeaders
            );
            CoinsExecuteDtoRs response = restTemplate.postForObject("/payments/execute", request, CoinsExecuteDtoRs.class);
            logger.info("response: {}", response);
            return response;
        } catch (IntegrationException e) {
            logger.info("error body: {}", e.getIntegrationErrorDto());
            return null;
        }
    }
}
