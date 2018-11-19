package gamestore.web.util;

import gamestore.domain.Game;
import gamestore.domain.User;
import gamestore.domain.dtos.DetailGameDto;
import gamestore.domain.dtos.GameDto;
import gamestore.domain.dtos.ReducedInfoGameDto;
import gamestore.domain.dtos.UserRegisterDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class MapperConfiguration {

    private ModelMapper mapper;

    public MapperConfiguration() {
        this.mapper = new ModelMapper();
    }
    public User mapUserRegisterDtoToUser(UserRegisterDto userRegisterDto, Class<User> userClass){
        return this.mapper.map(userRegisterDto,userClass);
    }

    public Game mapGameDtoToGame(GameDto gameDto, Class<Game> gameClass){
        return this.mapper.map(gameDto,gameClass);
    }

    public List<ReducedInfoGameDto> mapGamesToReducedInfoGames(List<Game> games,Class<ReducedInfoGameDto> reducedInfoGameDtoClass){
        return games.stream().
                map(game -> this.mapper.map(game,reducedInfoGameDtoClass)).
                collect(Collectors.toList());
    }
    public DetailGameDto mapGameToDetailGameDto(Game game,Class<DetailGameDto> gameDtoClass){
        return this.mapper.map(game,gameDtoClass);
    }
}
