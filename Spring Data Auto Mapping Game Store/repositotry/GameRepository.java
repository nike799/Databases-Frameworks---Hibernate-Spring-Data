package gamestore.repositotry;

import gamestore.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface GameRepository extends JpaRepository<Game,Long> {
    Optional<Game> findByTitle(String title);
    Optional<Game> findById(Long id);
    void removeAllById(Long id);

}
