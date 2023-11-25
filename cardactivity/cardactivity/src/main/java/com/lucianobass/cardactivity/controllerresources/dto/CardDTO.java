package com.lucianobass.cardactivity.controllerresources.dto;

import com.lucianobass.cardactivity.model.Card;

import java.io.Serializable;

public class CardDTO implements Serializable {
    private static final long serialVersionUIO = 1L;

    private Long id;
    private String name;
    private String numberCard;
    private String dateFinal;
    private String codSegurance;

    public CardDTO(Long id, String name, String numberCard, String dateFinal, String codSegurance) {
        this.id = id;
        this.name = name;
        this.numberCard = numberCard;
        this.dateFinal = dateFinal;
        this.codSegurance = codSegurance;
    }

    public CardDTO(Card card) {
        this.id = card.getId();
        this.name = card.getName();
        this.numberCard = card.getNumberCard();
        this.dateFinal = card.getDateFinal();
        this.codSegurance = card.getCodSegurance();
    }

    public CardDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = Card.generateNumberCard(19).replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "x");
    }

    public String getDateFinal() {
        return dateFinal;
    }

    public void setDateFinal(String dateFinal) {
        this.dateFinal = dateFinal;
    }

    public String getCodSegurance() {
        return codSegurance;
    }

    public void setCodSegurance(String codSegurance) {
        this.codSegurance = Card.generateNumberCard(3).replaceAll("(\\d)", "x");
    }


//    @JsonProperty("formattedNumberCard")
//    public String getFormattedNumberCard() {
//        return this.getNumberCard().replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "x");
//    }
}
