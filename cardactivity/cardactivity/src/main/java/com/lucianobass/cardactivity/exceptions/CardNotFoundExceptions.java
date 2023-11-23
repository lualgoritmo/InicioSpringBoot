package com.lucianobass.cardactivity.exceptions;

public class CardNotFoundExceptions extends RuntimeException {
    private final Long serialVersionUID = 1L;

    public CardNotFoundExceptions(Long id) {
        super("Cartão não encontrado com o id: " + id);
    }
}
