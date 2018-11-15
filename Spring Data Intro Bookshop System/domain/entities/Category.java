package bookshopsystemapp.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "categories")
public class Category extends BaseEntity {
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
