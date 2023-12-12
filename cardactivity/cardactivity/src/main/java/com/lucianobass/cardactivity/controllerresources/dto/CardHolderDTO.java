package com.lucianobass.cardactivity.controllerresources.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.TransactionDTO;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardHolderDTO implements Serializable {
    private static final long serialVersionUID = 1L; // não é utilizando em lugar nenhum

    @JsonIgnore
    private Long id; //Como é seu DTO e vc ta ignorando esse campo, ele não precisa ficar aqui .. pode ser removido

    private String name;
    private String documentNumber;
    //    @NotNull(message = "A data de nascimento não pode ser nula")
//    @NotBlank(message = "A data de nascimento não pode estar em branco")
    private String birthDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CardDTO card;
    private List<TransactionDTO> transaction;

    public CardHolderDTO(String name, String documentNumber, String birthDate) {
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

    public List<TransactionDTO> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<TransactionDTO> transaction) {
        this.transaction = transaction;
    }
}
