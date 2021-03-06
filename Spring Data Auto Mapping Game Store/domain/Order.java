package gamestore.domain;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private User user;
    private Set<Game> games;

    public Order() {
    }

    public Order(User user, Set<Game> games) {
        this.user = user;
        this.games = games;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToMany
    @JoinTable(name = "orders_games",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id")
    )
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

}
