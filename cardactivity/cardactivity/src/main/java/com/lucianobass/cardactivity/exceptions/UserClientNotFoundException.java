package com.lucianobass.cardactivity.exceptions;

public class UserClientNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UserClientNotFoundException(Long id) {
        super("Usuário não encontrado: " + id);
    }
}
