package mostwanted.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "districts")
public class District extends BaseEntity {

    private String name;
    private Town town;
    private Set<Race> races;

    public District() {
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    @ManyToOne(targetEntity = Town.class)
    public Town getTown() {
        return town;
    }

    @OneToMany(targetEntity = Race.class, mappedBy = "district")
    public Set<Race> getRaces() {
        return races;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public void setRaces(Set<Race> races) {
        this.races = races;
    }
}
