package com.boardcamp.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
        // nOT fOUND
        @ExceptionHandler({ NotFoundException.class })
        public ResponseEntity<String> handleNotFound(NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

        // Conflict
        @ExceptionHandler({ ConflictException.class })
        public ResponseEntity<String> handleConflict(ConflictException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }

        //BadRequest
        @ExceptionHandler({ BadRequestException.class })
        public ResponseEntity<String> handleBadRequest(BadRequestException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }

        //Unprocessable Entity
        @ExceptionHandler({ UnprocessableEntityException.class })
        public ResponseEntity<String> handleUnprocessableEntity(UnprocessableEntityException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
}
