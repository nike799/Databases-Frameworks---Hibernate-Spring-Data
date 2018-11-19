package gamestore.service;

public interface GameService {
    String addGame(String...args);
    String editGame(String...args);
    String deleteGameById(String...args);
    String viewAllGameWithTitleAndPrice();
    String getDetailsForASingleGame(String...args);
}
