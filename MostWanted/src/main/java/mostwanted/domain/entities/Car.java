package mostwanted.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "cars")
public class Car extends BaseEntity {

    private String brand;
    private String model;
    private BigDecimal price;
    private Integer yearOfProduction;
    private Double maxSpeed;
    private Double zeroToSixty;
    private Racer racer;
    private Set<RaceEntry> entries;

    public Car() {
    }

    @Column(nullable = false)
    public String getBrand() {
        return brand;
    }

    @Column(nullable = false)
    public String getModel() {
        return model;
    }

    @Column
    public BigDecimal getPrice() {
        return price;
    }

    @Column(nullable = false)
    public Integer getYearOfProduction() {
        return yearOfProduction;
    }

    @Column
    public Double getMaxSpeed() {
        return maxSpeed;
    }

    @Column
    public Double getZeroToSixty() {
        return zeroToSixty;
    }

    @ManyToOne(targetEntity = Racer.class)
    public Racer getRacer() {
        return racer;
    }

    @OneToMany(targetEntity = RaceEntry.class, mappedBy = "car")
    public Set<RaceEntry> getEntries() {
        return entries;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setYearOfProduction(Integer yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setZeroToSixty(Double zeroToSixty) {
        this.zeroToSixty = zeroToSixty;
    }

    public void setRacer(Racer racer) {
        this.racer = racer;
    }

    public void setEntries(Set<RaceEntry> entries) {
        this.entries = entries;
    }
}
