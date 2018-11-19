package gamestore.service;

import gamestore.domain.Game;
import gamestore.domain.ShoppingCart;
import gamestore.domain.User;
import gamestore.domain.dtos.UserRegisterDto;
import gamestore.repositotry.GameRepository;
import gamestore.repositotry.OrderRepository;
import gamestore.repositotry.UserRepository;
import gamestore.web.util.MapperConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MapperConfiguration mapper;
    private Validator validator;
    private User currentlyLoggedInUser;
    private ShoppingCart shoppingCart;
    private final GameRepository gameRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MapperConfiguration mapper, GameRepository gameRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.orderRepository = orderRepository;
        this.shoppingCart = new ShoppingCart();
        this.gameRepository = gameRepository;
        this.setValidator();

    }


    @Override
    public String registerUser(String... args) {
        StringBuilder result = new StringBuilder();
        if (!args[2].equals(args[3])) {
            return result.append("Confirmed password doesn't match provided password").toString();
        }
        UserRegisterDto userRegisterDto = createUser(args);
        Set<ConstraintViolation<UserRegisterDto>> constraintViolations = this.validator.validate(userRegisterDto);
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<UserRegisterDto> cv : constraintViolations) {
                result.append(cv.getMessage()).append(System.lineSeparator());
            }
        } else {
            if (this.userRepository.count() == 0) {
                userRegisterDto.setAdmin(true);
            } else {
                userRegisterDto.setAdmin(false);
            }
            User entity = this.mapper.mapUserRegisterDtoToUser(userRegisterDto, User.class);
            if (userRepository.findByEmail(args[1]).orElse(null) != null) {
                return result.append("User with the same email is already registered.").
                        append(System.lineSeparator()).toString();
            }
            this.userRepository.saveAndFlush(entity);
            result.append(String.format("%s was registered", args[4])).append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public String loginUser(String... args) {
        StringBuilder result = new StringBuilder();
        User user = this.userRepository.findByEmail(args[1]).orElse(null);
        if (user == null) {
            result.append("No such user exists.").append(System.lineSeparator());
        } else {
            if (!user.getPassword().equals(args[2])) {
                return result.append("Incorrect password.").append(System.lineSeparator()).toString();
            } else {
                if (currentlyLoggedInUser != null) {
                    if (currentlyLoggedInUser.getEmail().equals(user.getEmail())) {
                        return result.append("User is already logged in.").append(System.lineSeparator()).toString();
                    }
                }
                currentlyLoggedInUser = user;
                result.append(String.format("Successfully logged in %s", user.getFullName())).append(System.lineSeparator());
            }
        }
        return result.toString();
    }

    @Override
    public String logoutUser() {
        StringBuilder result = new StringBuilder();
        if (currentlyLoggedInUser == null) {
            result.append("Cannot log out. No user was logged in.").append(System.lineSeparator());
        } else {
            result.append(String.format("User %s successfully logged out", currentlyLoggedInUser.getFullName())).
                    append(System.lineSeparator());
            currentlyLoggedInUser = null;
        }
        return result.toString();
    }

    @Override
    public String addItemToShoppingCart(String... args) {
        StringBuilder result = new StringBuilder();
        Game game = gameRepository.findByTitle(args[1]).orElse(null);
        if (game == null) {
            result.append(String.format("Item %s doesn't exist into database.", args[1])).
                    append(System.lineSeparator());
        } else {
            if (currentlyLoggedInUser == null) {
                result.append("Please log in first!").append(System.lineSeparator());
            } else {
                List<Game> found = this.shoppingCart.getOrder().getGames().stream().
                        filter(g -> g.getTitle().equals(game.getTitle())).collect(Collectors.toList());
                if (found.size() > 0) {
                    result.append(String.format("Item %s is already added to cart.", args[1])).append(System.lineSeparator());
                } else {
                    this.shoppingCart.setCurrentlyLoggedInUser(currentlyLoggedInUser);
                    this.shoppingCart.getOrder().getGames().add(game);
                    result.append(String.format("%s added to cart.", args[1])).append(System.lineSeparator());
                }
            }
        }
        return result.toString();
    }

    @Override
    public String removeItemFromShoppingCart(String... args) {
        StringBuilder result = new StringBuilder();
        Game game = gameRepository.findByTitle(args[1]).orElse(null);
        if (game == null) {
            result.append(String.format("Item %s doesn't exist into database.", args[1])).
                    append(System.lineSeparator());
        } else {
            if (currentlyLoggedInUser == null) {
                result.append("Please log in first!").append(System.lineSeparator());
            } else {
                List<Game> found = this.shoppingCart.getOrder().getGames().stream().
                        filter(g -> g.getTitle().equals(game.getTitle())).
                        collect(Collectors.toList());
                if (found.size()>0){
                    this.shoppingCart.getOrder().getGames().removeIf(g->g.getTitle().equals(game.getTitle()));
                    result.append(String.format("%s removed from cart.",args[1])).append(System.lineSeparator());
                }else{
                    result.append(String.format("Nothing to remove! No item with title %s into shopping cart.",args[1])).
                            append(System.lineSeparator());
                }
            }
        }
        return result.toString();
    }

    @Override
    public String buyItems() {
        StringBuilder result = new StringBuilder();
        if (currentlyLoggedInUser == null) {
         return result.append("Please log in first!").append(System.lineSeparator()).toString();
        }
        if (shoppingCart.getOrder().getGames().size()>0){
            orderRepository.save(shoppingCart.getOrder());
            currentlyLoggedInUser.getOrders().add(shoppingCart.getOrder());
            userRepository.saveAndFlush(currentlyLoggedInUser);
            result.append(String.format("Successfully bought games:\n" +
                    "-%s",shoppingCart.getOrder().getGames().stream().
                    map(Game::getTitle).collect(Collectors.joining("-\n"))));
            shoppingCart.getOrder().getGames().clear();
        }else {
            result.append("Nothing to buy. Shopping cart is empty.").append(System.lineSeparator());
        }
        return result.toString();
    }

    private UserRegisterDto createUser(String... args) {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setEmail(args[1]);
        userRegisterDto.setPassword(args[2]);
        userRegisterDto.setConfirmedPassword(args[3]);
        userRegisterDto.setFullName(args[4]);
        return userRegisterDto;
    }

    private void setValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }
}
