package ru.stepup.spring.coins.core.configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.stepup.spring.coins.core.configurations.properties.ExecutorProperties;
import ru.stepup.spring.coins.core.configurations.properties.ProductProperties;
import ru.stepup.spring.coins.core.exceptions.RestTemplateProductResponseErrorHandler;
import ru.stepup.spring.coins.core.exceptions.RestTemplateResponseErrorHandler;
import ru.stepup.spring.coins.core.integrations.ExecutorIntegration;
import ru.stepup.spring.coins.core.integrations.ExecutorIntegrationRestTemplate;
import ru.stepup.spring.coins.core.integrations.IProductIntegration;
import ru.stepup.spring.coins.core.integrations.ProductIntegration;

@Configuration
public class IntegrationsConfig {
    @Bean
    @ConditionalOnMissingBean(name = "executorIntegrationRestClient")
    public ExecutorIntegration executorIntegration(
            ExecutorProperties executorProperties,
            RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(executorProperties.getClient().getUrl())
                .setConnectTimeout(executorProperties.getClient().getConnectTimeout())
                .setReadTimeout(executorProperties.getClient().getReadTimeout())
                .errorHandler(restTemplateResponseErrorHandler)
                .build();

        return new ExecutorIntegrationRestTemplate(restTemplate);
    }

    @Bean
    public IProductIntegration productIntegration(ProductProperties productProperties,
                                                  RestTemplateProductResponseErrorHandler restTemplateProductResponseErrorHandler) {

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(productProperties.getClientProduct().getUrl())
                .setConnectTimeout(productProperties.getClientProduct().getConnectTimeout())
                .setReadTimeout(productProperties.getClientProduct().getReadTimeout())
                .errorHandler(restTemplateProductResponseErrorHandler)
                .build();

        return new ProductIntegration(restTemplate);
    }


//    @Bean
//    public ExecutorIntegration executorIntegrationRestClient(
//            ExecutorProperties executorProperties
//    ) {
//        RestClient restClient = RestClient.create(executorProperties.getClient().getUrl());
//        return new ExecutorIntegrationRestClient(restClient);
//    }
}