package app.domain.entities;

import app.domain.interfaces.Entitable;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer implements Entitable {
    String name;
    String email;
    String creditCardNumber;
    Set<Sale> sales;

    public Customer() {
    }

    public Customer(String name, String email, String creditCardNumber, Set<Sale> sales) {
        this.name = name;
        this.email = email;
        this.creditCardNumber = creditCardNumber;
        this.sales = sales;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "credit_card_number")
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @OneToMany(targetEntity = Sale.class, mappedBy = "customer")
    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

}
