package gamestore.web.controller;

import gamestore.service.GameService;
import gamestore.service.UserService;
import gamestore.web.util.InputReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class GameStoreController implements CommandLineRunner {
    private final InputReader reader;
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public GameStoreController(InputReader reader, UserService userService, GameService gameService) {
        this.reader = reader;
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            System.out.println("Enter command:");
            String[] inputArgs = reader.readLine().split("\\|");
            switch (inputArgs[0]) {
                case "RegisterUser":
                    System.out.println(userService.registerUser(inputArgs));
                    break;
                case "LoginUser":
                    System.out.println(userService.loginUser(inputArgs));
                    break;
                case "Logout":
                case "LogoutUser":
                    System.out.println(userService.logoutUser());
                    break;
                case "AddGame":
                    System.out.println(gameService.addGame(inputArgs));
                    break;
                case "EditGame":
                    System.out.println(gameService.editGame(inputArgs));
                    break;
                case "DeleteGame":
                    System.out.println(gameService.deleteGameById(inputArgs));
                    break;
                case "AllGame":
                    System.out.println(gameService.viewAllGameWithTitleAndPrice());
                    break;
                case "DetailGame":
                    System.out.println(gameService.getDetailsForASingleGame(inputArgs));
                    break;
                case "AddItem":
                    System.out.println(userService.addItemToShoppingCart(inputArgs));
                    break;
                case "RemoveItem":
                    System.out.println(userService.removeItemFromShoppingCart(inputArgs));
                    break;
                case "BuyItem":
                    System.out.println(userService.buyItems());
                    break;
            }
        }

    }
}
