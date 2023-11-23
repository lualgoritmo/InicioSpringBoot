package com.lucianobass.cardactivity.controllerresources;

import com.lucianobass.cardactivity.exceptions.CardNotFoundExceptions;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(CardNotFoundExceptions.class)
    public String handleNotFoundExcetption(CardNotFoundExceptions ex) {
        return ex.getMessage();
    }
}
