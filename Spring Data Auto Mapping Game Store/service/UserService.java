package gamestore.service;

public interface UserService {
    String registerUser(String... args);

    String loginUser(String... args);

    String logoutUser();

    String addItemToShoppingCart(String... args);

    String removeItemFromShoppingCart(String... args);

    String buyItems();

}
