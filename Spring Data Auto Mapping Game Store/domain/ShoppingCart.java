package gamestore.domain;



import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

@Configuration
public class ShoppingCart {
    private User currentlyLoggedInUser;
    private Order order;


    public ShoppingCart() {
        this.order = new Order();
        order.setGames(new HashSet<>());
    }

    public User getCurrentlyLoggedInUser() {
        return currentlyLoggedInUser;
    }

    public void setCurrentlyLoggedInUser(User currentlyLoggedInUser) {
        this.currentlyLoggedInUser = currentlyLoggedInUser;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
