package mostwanted.repository;

import mostwanted.domain.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TownRepository extends JpaRepository<Town,Integer> {
    Optional<Town> findByName(String name);
    @Query("select t.name, count(r.id) as countRacers from mostwanted.domain.entities.Town t " +
            "join mostwanted.domain.entities.Racer r on r.homeTown = t.id group by t.id " +
            "order by count(r.id) desc, t.name asc ")
   Object[][] findAllTownWithRacers();
}
