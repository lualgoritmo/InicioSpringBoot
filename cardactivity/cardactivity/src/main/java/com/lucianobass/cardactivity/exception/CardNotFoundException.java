package com.lucianobass.cardactivity.exception;

public class CardNotFoundException extends RuntimeException {
    private static final Long serialVersionuio = 1L;

    public CardNotFoundException(Long id) {
       super("Cartão não encontrado: " + id);
    }
}
