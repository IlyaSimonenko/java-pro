package ru.stepup.spring.coins.core.exceptions;

public class ResourceDoesNotMeetConditionsException extends RuntimeException {
    public ResourceDoesNotMeetConditionsException(String message) {
        super(message);
    }
}
