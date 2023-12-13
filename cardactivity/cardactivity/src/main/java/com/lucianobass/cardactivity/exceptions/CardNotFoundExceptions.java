package com.lucianobass.cardactivity.exceptions;

public class CardNotFoundExceptions extends RuntimeException {

    public CardNotFoundExceptions(Long id) {
        super("Cardholder não encontrado id: " + id);
    }

}
