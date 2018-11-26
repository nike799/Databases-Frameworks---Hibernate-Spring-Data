package cardealer.repository;

import cardealer.domain.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car[] findAllByMakeLikeOrderByModelAscTravelledDistanceDesc(String brand);

    @Query("SELECT c from cardealer.domain.model.Car c where c.make like :make order by c.model asc,c.travelledDistance desc")
    Car[] findAllToyotaOrderByModel(@Param(value = "make") String model);
}
