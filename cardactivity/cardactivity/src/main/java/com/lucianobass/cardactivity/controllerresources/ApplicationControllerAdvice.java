package com.lucianobass.cardactivity.controllerresources;

import com.lucianobass.cardactivity.exceptions.CardNotFoundExceptions;
import com.lucianobass.cardactivity.exceptions.TransactionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(CardNotFoundExceptions.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundExcetption(CardNotFoundExceptions ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleExceptions(Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundExcetption(TransactionNotFoundException ex) {
        return ex.getMessage();
    }
}
