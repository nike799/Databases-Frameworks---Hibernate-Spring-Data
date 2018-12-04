package mostwanted.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "towns")
public class Town extends BaseEntity {
    private String name;
    private Set<District> districts;

    public Town() {
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    @OneToMany(targetEntity = District.class, mappedBy = "town")
    public Set<District> getDistricts() {
        return districts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDistricts(Set<District> districts) {
        this.districts = districts;
    }
}
