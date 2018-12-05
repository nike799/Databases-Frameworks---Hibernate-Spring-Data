package mostwanted.repository;

import mostwanted.domain.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car,Integer> {
Optional<Car> findByBrandAndModelAndYearOfProduction(String brand,String model,Integer yearOfProduction);
Optional<Car> findById(Integer id);
}
