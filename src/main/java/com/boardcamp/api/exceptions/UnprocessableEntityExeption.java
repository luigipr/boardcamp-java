package com.boardcamp.api.exceptions;

public class UnprocessableEntityExeption extends RuntimeException {
    public UnprocessableEntityExeption (String message) {
        super(message);
    }
}
