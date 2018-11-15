package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "credit_cards")
public class CreditCard extends BillingDetail {
    private String cardType;
    private LocalDate expirationMonth;
    private LocalDate expirationYear;

    public CreditCard() {
    }

    public CreditCard(User user, String number, String cardType, LocalDate expirationMonth, LocalDate expirationYear) {
        super(user, number);
        this.cardType = cardType;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    @Column(name = "card_type")
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Column(name = "expiration_month")
    protected LocalDate getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(LocalDate expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @Column(name = "expiration_year")
    public LocalDate getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(LocalDate expirationYear) {
        this.expirationYear = expirationYear;
    }
}
