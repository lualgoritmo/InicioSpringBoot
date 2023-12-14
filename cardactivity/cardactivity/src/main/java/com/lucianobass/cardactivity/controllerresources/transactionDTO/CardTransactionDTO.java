package com.lucianobass.cardactivity.controllerresources.transactionDTO;

public class CardTransactionDTO {
    private String numberCard;
    private String expiration;

    public CardTransactionDTO() {
    }

    public CardTransactionDTO(String numberCard, String expiration) {
        this.numberCard = numberCard;
        this.expiration = expiration;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

}
