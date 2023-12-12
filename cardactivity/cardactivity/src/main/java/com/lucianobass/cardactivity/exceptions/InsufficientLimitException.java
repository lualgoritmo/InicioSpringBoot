package com.lucianobass.cardactivity.exceptions;

public class InsufficientLimitException extends RuntimeException {
    public InsufficientLimitException(String message) {
        super(message);
    }
}
