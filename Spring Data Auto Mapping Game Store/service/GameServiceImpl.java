package gamestore.service;

import gamestore.domain.Game;
import gamestore.domain.dtos.DetailGameDto;
import gamestore.domain.dtos.GameDto;
import gamestore.domain.dtos.ReducedInfoGameDto;
import gamestore.repositotry.GameRepository;
import gamestore.web.util.MapperConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final MapperConfiguration mapper;
    private Validator validator;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, MapperConfiguration mapper) {
        this.gameRepository = gameRepository;
        this.mapper = mapper;
        this.setValidator();
    }

    private void setValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Override
    public String viewAllGameWithTitleAndPrice() {
        StringBuilder result = new StringBuilder();
        this.mapper.mapGamesToReducedInfoGames(this.gameRepository.findAll(), ReducedInfoGameDto.class)
                .forEach(reducedInfoGameDto -> result.append(reducedInfoGameDto.getTitle()).append(" ").append(reducedInfoGameDto.getPrice()).
                        append(System.lineSeparator()));

        return result.toString();
    }

    @Override
    public String getDetailsForASingleGame(String... args) {
        StringBuilder result = new StringBuilder();
        Game game = gameRepository.findByTitle(args[1]).orElse(null);
        if (game != null) {
            DetailGameDto detailGameDto = this.mapper.mapGameToDetailGameDto(game,DetailGameDto.class);
            result.append(String.format(""+
                    "Title: %s\n" +
                    "Price: %.2f \n" +
                    "Description: %s \n" +
                    "Release date: %s\n",
                    detailGameDto.getTitle(),
                    detailGameDto.getPrice(),
                    detailGameDto.getDescription(),
                    detailGameDto.getReleaseDate()));
        }else {
            result.append(String.format("Game with title %s doesn't exists int database",args[1])).
                    append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public String addGame(String... args) {
        StringBuilder result = new StringBuilder();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(args[7], dateTimeFormatter);
        GameDto gameDto = new GameDto();
        gameDto.setTitle(args[1]);
        gameDto.setPrice(new BigDecimal(args[2]));
        gameDto.setSize(new BigDecimal(args[3]));
        gameDto.setTrailer(args[4]);
        gameDto.setImageThumbnail(args[5]);
        gameDto.setDescription(args[6]);
        gameDto.setReleaseDate(date);
        Set<ConstraintViolation<GameDto>> constraintViolations = this.validator.validate(gameDto);

        if (constraintViolations.size() > 0) {
            for (ConstraintViolation cv : constraintViolations) {
                result.append(cv.getMessage()).append(System.lineSeparator());
            }
        } else {
            Game game = this.mapper.mapGameDtoToGame(gameDto, Game.class);
            if (this.gameRepository.findByTitle(game.getTitle()).orElse(null) != null) {
                result.append(String.format("The game with title %s is already added into database.", game.getTitle())).
                        append(System.lineSeparator());
            } else {
                this.gameRepository.saveAndFlush(game);
                result.append(String.format("Added %s", game.getTitle())).append(System.lineSeparator());
            }
        }
        return result.toString();
    }

    @Override
    public String editGame(String... args) {
        StringBuilder result = new StringBuilder();
        Game game = this.gameRepository.findById(Long.parseLong(args[1])).orElse(null);
        if (game != null) {
            List<String> arguments = Arrays.stream(args).skip(2).collect(Collectors.toList());
            for (String argument : arguments) {
                String field = argument.split("=")[0];
                String value = argument.split("=")[1];
                switch (field) {
                    case "title":
                        game.setTitle(value);
                        break;
                    case "trailer":
                        game.setTrailer(value);
                        break;
                    case "size":
                        game.setSize(new BigDecimal(value));
                        break;
                    case "price":
                        game.setPrice(new BigDecimal(value));
                        break;
                    case "description":
                        game.setDescription(value);
                        break;
                    case "imageThumbnail":
                        game.setImageThumbnail(value);
                        break;
                }

            }
            this.gameRepository.save(game);
            result.append(String.format("Game with id %d was edited.", game.getId())).append(System.lineSeparator());
        } else {
            result.append(String.format("Game with id %s doesn't exists.", args[1])).append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public String deleteGameById(String... args) {
        StringBuilder result = new StringBuilder();
        Long id = Long.parseLong(args[1]);
        Game game = gameRepository.findById(id).orElse(null);
        if (game != null) {
            gameRepository.removeAllById(id);
            result.append(String.format("Game with id %d was removed from database.", id)).
                    append(System.lineSeparator());
        } else {
            result.append(String.format("Game with id %s doesn't exists.", args[1])).append(System.lineSeparator());
        }
        return result.toString();
    }
}
