package cardealer.repository;
import cardealer.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("select c from cardealer.domain.model.Customer c order by c.dateOfBirth asc")
Customer[] findAllOrOrderByBirthDateAscYoungDriverIsFalse();
    @Query(value = "       SELECT c.name,count(s.customer_id) AS bought_cars,Sum(p.price)*count(s.customer_id) AS price\n" +
            "       FROM customers AS c\n" +
            "              JOIN sales AS s ON c.id = s.customer_id\n" +
            "              JOIN cars AS car ON s.car_id = car.id\n" +
            "              JOIN cars_parts AS cp ON car.id = cp.cars_id\n" +
            "              JOIN parts AS p ON cp.parts_id = p.id\n" +
            "       GROUP BY c.id",nativeQuery = true)
    Object[][] getAllCustomerWithBoughtCars();

}
