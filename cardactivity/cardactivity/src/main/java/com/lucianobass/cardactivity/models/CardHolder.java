package com.lucianobass.cardactivity.models;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "tb_cardholder")
public class CardHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String documentNumber;
    private String birthDate;
    @OneToOne(mappedBy = "cardHolder", cascade = CascadeType.ALL)
    private Card card;

    public CardHolder(String name, String documentNumber, String birthDate) {
        this.name = name;
        this.documentNumber = documentNumber;
        this.birthDate = birthDate;
    }

    public CardHolder() {
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

    public String generateNumberCard(int number) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    @PrePersist
    public void prePersist() {
        if (this.card == null) {
            this.card = new Card();
            this.card.setNumberCard(generateNumberCard(16));  // Lógica para gerar o número do cartão
            this.card.setCardExpiration("2023-12");  // Lógica para definir a data de vencimento
            this.card.setCardActive(true);  // Definindo o cartão como ativo
            this.card.setCardHolder(this);
        }
    }
}