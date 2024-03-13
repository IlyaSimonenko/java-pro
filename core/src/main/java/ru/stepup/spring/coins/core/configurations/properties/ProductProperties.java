package ru.stepup.spring.coins.core.configurations.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "integrations.executor")
public class ProductProperties {

    private RestTemplateProperties clientProduct;

    public RestTemplateProperties getClientProduct() {
        return clientProduct;
    }

    @ConstructorBinding
    public ProductProperties(RestTemplateProperties clientProduct) {
        this.clientProduct = clientProduct;
    }

}
