package app.entities;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BillingDetail {
    private User user;
    private String number;

    public BillingDetail() {
    }

    protected BillingDetail(User user, String number) {
        this.user = user;
        this.number = number;
    }


    @ManyToOne(targetEntity = User.class)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Id
    public String  getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
