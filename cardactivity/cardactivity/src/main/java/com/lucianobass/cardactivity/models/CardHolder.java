package com.lucianobass.cardactivity.models;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "tb_cardholder")
public class CardHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCardHolder;

    //@NotBlank(message = "Escreva o seu nome")
    private String name;

    //@NotBlank(message = "Digite o seu documento")
    private String documentNumber;

    //@NotBlank(message = "Data de nascimento")
    private String birthDate;

    @OneToOne(mappedBy = "cardHolder", cascade = CascadeType.ALL)
    //@OneToOne(mappedBy = "cardHolder")
    //@JsonManagedReference
    private Card card;
    public CardHolder(String name, String documentNumber, String birthDate) {
        this.name = name;
        this.documentNumber = documentNumber;
        this.birthDate = birthDate;
        this.createCard();

    }

    private void createCard() {
        this.card = new Card();
        System.out.println("Creating card for CardHolder: " + this.name);
        this.card.setNumberCard(generateNumberAleatory(16)
                .replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "x"));
        this.card.setAvailableLimit("100.00");
        this.card.setCardExpiration("30/02");
        this.card.setCardLimit(100.0);
        this.card.setCardCVV(generateNumberAleatory(3).replaceAll("(\\d)", "x"));
        this.card.setCardActive(false);
        this.card.setCardHolder(this);
    }

    public CardHolder() {
    }

    public Long getIdCardHolder() {
        return idCardHolder;
    }

    public void setIdCardHolder(Long idCardHolder) {
        this.idCardHolder = idCardHolder;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
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

//    @PrePersist
//    public void prePersist() {
//        try {
//            System.out.println("ID no prePersist: " + this.idCardHolder);
//            System.out.println("Card no prePersist: " + this.card);
//            if (this.card == null && this.idCardHolder == null) {
//                this.card = new Card();
//                this.card.setNumberCard(generateNumberAleatory(16)
//                        .replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "x"));
//                this.card.setAvailableLimit("100.00");
//                this.card.setCardExpiration("30/02");
//                this.card.setCardLimit(100.0);
//                this.card.setCardCVV(generateNumberAleatory(3).replaceAll("(\\d)", "x"));
//                this.card.setCardActive(false);
//                this.card.setCardHolder(this);
//            }
//            System.out.println("ID no prePersist: " + this.idCardHolder);
//            System.out.println("Card no prePersist: " + this.card);
//        } catch (Exception ex) {
//            System.out.println(" Erro no PREPERSIST" + ex.getMessage());
//        }
//    }

    public static String generateNumberAleatory(int number) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

}
