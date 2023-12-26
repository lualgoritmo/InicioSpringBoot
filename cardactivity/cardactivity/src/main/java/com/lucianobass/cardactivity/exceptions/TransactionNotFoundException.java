package com.lucianobass.cardactivity.exceptions;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(String documentNumber) {
        super("Esse número de cartão não existe: " + documentNumber);
    }
}
