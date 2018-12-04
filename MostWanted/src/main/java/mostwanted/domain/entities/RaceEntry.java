package mostwanted.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "race_entries")
public class RaceEntry extends BaseEntity {

    private Boolean hasFinished;
    private Double finishTime;
    private Car car;
    private Race race;
    private Racer racer;

    public RaceEntry() {
    }

    @Column
    public Boolean getHasFinished() {
        return hasFinished;
    }

    @Column
    public Double getFinishTime() {
        return finishTime;
    }

    @ManyToOne(targetEntity = Car.class)
    public Car getCar() {
        return car;
    }

    @ManyToOne(targetEntity = Race.class)
    public Race getRace() {
        return race;
    }

    @ManyToOne(targetEntity = Racer.class)
    public Racer getRacer() {
        return racer;
    }

    public void setHasFinished(Boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public void setFinishTime(Double finishTime) {
        this.finishTime = finishTime;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setRacer(Racer racer) {
        this.racer = racer;
    }

    public void setRace(Race race) {
        this.race = race;
    }
}
