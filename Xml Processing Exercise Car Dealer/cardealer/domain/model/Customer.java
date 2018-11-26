package cardealer.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "customers")
public class Customer extends BaseEntity {
    private String name;
    private LocalDate birthDate;
    private boolean isYoungDriver;
    private List<Sale> sales;


    public Customer() {
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public LocalDate getDateOfBirth() {
        return birthDate;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.birthDate = dateOfBirth;
    }

    @Column(name = "is_young_driver")
    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    @OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}
