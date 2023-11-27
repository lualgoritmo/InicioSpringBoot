package com.lucianobass.cardactivity.exceptions;

public class CardNotFoundExceptions extends RuntimeException {
    private final Long serialVersionUID = 1L;

    public CardNotFoundExceptions(Long id) {
        super("Cardholder não encontrado id: " + id);
    }
}
