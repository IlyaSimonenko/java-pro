package ru.stepup.spring.coins.core.exceptions;

public class IntegrationException extends RuntimeException {
    private IntegrationErrorDto integrationErrorDto;

    public IntegrationErrorDto getIntegrationErrorDto() {
        return integrationErrorDto;
    }

    public IntegrationException(String message, IntegrationErrorDto integrationErrorDto) {
        super(message);
        this.integrationErrorDto = integrationErrorDto;
    }
}
