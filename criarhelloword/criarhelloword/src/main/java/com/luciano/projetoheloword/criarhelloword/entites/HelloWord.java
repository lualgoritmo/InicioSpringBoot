package com.luciano.projetoheloword.criarhelloword.entites;

public class HelloWord {
    private String message;

    public HelloWord(String message) {
        this.message = message;
    }

    public HelloWord() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
