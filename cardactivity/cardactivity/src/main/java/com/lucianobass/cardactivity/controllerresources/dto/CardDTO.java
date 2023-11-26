import com.lucianobass.cardactivity.models.Card;

public class CardDTO {

    private Long id;
    private String numberCard;
    private String cardExpiration;
    private String availableLimit;
    private String cardLimit;
    private String cardCVV;
    private boolean cardActive;

    public CardDTO() {
    }

    public CardDTO(Card entity) {
        this.id = entity.getId();
        this.numberCard = entity.getNumberCard();
        this.cardExpiration = entity.getCardExpiration();
        this.availableLimit = entity.getAvailableLimit();
        this.cardLimit = entity.getCardLimit();
        this.cardCVV = entity.getCardCVV();
        this.cardActive = entity.isCardActive();
    }

    public CardDTO(Long id, String numberCard, String expiration, String availableLimit,
                   String limit, String cvv, boolean active) {
        this.id = id;
        this.numberCard = numberCard;
        this.cardExpiration = expiration;
        this.availableLimit = availableLimit;
        this.cardLimit = limit;
        this.cardCVV = cvv;
        this.cardActive = active;
    }

    public Long getId() {
        return id;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public String getCardExpiration() {
        return cardExpiration;
    }

    public void setCardExpiration(String cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    public String getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(String availableLimit) {
        this.availableLimit = availableLimit;
    }

    public String getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(String cardLimit) {
        this.cardLimit = cardLimit;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public boolean isCardActive() {
        return cardActive;
    }

    public void setCardActive(boolean cardActive) {
        this.cardActive = cardActive;
    }

    public String getFormattedNumberCard() {
        return this.getNumberCard().replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "x");
    }
}

