package ru.stepup.spring.coins.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.stepup.spring.coins.core.dtos.ErrorDto;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ErrorDto("RESOURCE_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceDoesNotMeetConditionsException.class)
    public ResponseEntity<ErrorDto> handleResourceDoesNotMeetConditionsException(ResourceDoesNotMeetConditionsException e) {
        return new ResponseEntity<>(new ErrorDto("RESOURCE_DOES_NOT_MEET_CONDITIONS", e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDto> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(new ErrorDto(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
