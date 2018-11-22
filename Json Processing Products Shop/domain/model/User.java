package productsshop.domain.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    //	Users have an id, first name (optional) and last name (at least 3 characters) and age (optional).
    private String firstName;
    private String lastName;
    private Integer age;
    private Set<User> friends;
    private List<Product> soldProducts;

    public User() {
    }

    public User(String firstName, String lastName, Integer age, Set<User> friends, List<Product> soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.friends = friends;
        this.soldProducts = soldProducts;
    }

    @Column
    @Length(min = 3)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @ManyToMany
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @OneToMany(mappedBy = "seller",fetch = FetchType.EAGER)
    public List<Product> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<Product> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
