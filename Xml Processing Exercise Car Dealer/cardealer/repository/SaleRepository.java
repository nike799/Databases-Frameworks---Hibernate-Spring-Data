package cardealer.repository;

import cardealer.domain.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleRepository extends JpaRepository<Sale,Long> {
    @Query(value = "SELECT\n" +
            "       car.make,\n" +
            "       car.model,\n" +
            "       car.travelled_distance,\n" +
            "       c.name,\n" +
            "       s.discount,\n" +
            "       SUM(p.price)                                       AS price,\n" +
            "       (SUM(p.price) - (SUM(p.price) * s.discount) / 100) AS price_with_discount\n" +
            "FROM sales s\n" +
            "       JOIN customers c ON s.customer_id = c.id\n" +
            "       JOIN cars car ON s.car_id = car.id\n" +
            "       JOIN cars_parts cp ON car.id = cp.cars_id\n" +
            "       JOIN parts p ON cp.parts_id = p.id\n" +
            "GROUP BY s.id",nativeQuery = true)
    Object[][] findAllSalesWithAppliedDiscount();

}
