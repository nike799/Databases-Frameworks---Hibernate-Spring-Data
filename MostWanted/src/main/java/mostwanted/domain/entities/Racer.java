package mostwanted.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "racers")
public class Racer extends BaseEntity {

    private String name;
    private Integer age;
    private BigDecimal bounty;
    private Town homeTown;
    private Set<Car> cars;
    private Set<RaceEntry> entries;

    public Racer() {
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    @Column
    public Integer getAge() {
        return age;
    }

    @Column
    public BigDecimal getBounty() {
        return bounty;
    }

    @ManyToOne(targetEntity = Town.class)
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    public Town getHomeTown() {
        return homeTown;
    }

    @OneToMany(targetEntity = Car.class, mappedBy = "racer")
    public Set<Car> getCars() {
        return cars;
    }

    @OneToMany(targetEntity = RaceEntry.class, mappedBy = "racer")
    public Set<RaceEntry> getEntries() {
        return entries;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setBounty(BigDecimal bounty) {
        this.bounty = bounty;
    }

    public void setHomeTown(Town homeTown) {
        this.homeTown = homeTown;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public void setEntries(Set<RaceEntry> entries) {
        this.entries = entries;
    }
}
