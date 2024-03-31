package ru.simonenko.ilya.spring.exceptions;

public class BadRequestException extends RuntimeException {
    private String code;

    public String getCode() {
        return code;
    }

    public BadRequestException(String message, String code) {
        super(message);
        this.code = code;
    }
}
