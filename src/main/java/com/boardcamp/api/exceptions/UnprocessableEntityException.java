package com.boardcamp.api.exceptions;

public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException (String message) {
        super(message);
    }
}
