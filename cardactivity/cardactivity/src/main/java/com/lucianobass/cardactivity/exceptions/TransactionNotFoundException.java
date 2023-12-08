package com.lucianobass.cardactivity.exceptions;

public class TransactionNotFoundException extends RuntimeException {
    private final Long serialVersionUID = 1L;

    public TransactionNotFoundException(String documentNumber) {
        super("Esse número de cartão não existe: " + documentNumber);
    }
}
