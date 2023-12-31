package com.lucianobass.cardactivity.controllerresources.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class CardHolderDTO implements Serializable {
   // não é utilizando em lugar nenhum
    //Como é seu DTO e vc ta ignorando esse campo, ele não precisa ficar aqui .. pode ser removido


    private String name;
    private String documentNumber;
    @NotBlank(message = "A data de nascimento não pode estar em branco")
    private String birthDate;
    private CardDTO card;

    public CardHolderDTO(String name, String documentNumber, String birthDate) {
        this.name = name;
        this.documentNumber = documentNumber;
        this.birthDate = birthDate;
    }

    public CardHolderDTO() {

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
