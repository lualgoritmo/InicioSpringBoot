package com.lucianobass.cardactivity.controllerresources.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class CardHolderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;
    private String name;
    private String documentNumber;
//    @NotNull(message = "A data de nascimento não pode ser nula")
//    @NotBlank(message = "A data de nascimento não pode estar em branco")
    private String birthDate;
    private CardDTO card;

    public CardHolderDTO(Long id, String name, String documentNumber, String birthDate) {
        this.id = id;
        this.name = name;
        this.documentNumber = documentNumber;
        this.birthDate = birthDate;
    }

    public CardHolderDTO() {

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

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public CardDTO getCard() {
        return card;
    }

    public void setCard(CardDTO card) {
        this.card = card;
    }
}
