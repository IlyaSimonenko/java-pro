package ru.simonenko.ilya.spring.exceptions;

public class ResourceDoesNotMeetConditionsException extends RuntimeException {
    public ResourceDoesNotMeetConditionsException(String message) {
        super(message);
    }
}
