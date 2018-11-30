package productsshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import productsshop.domain.model.Product;
import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Product[] findAllByPriceIsBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal after,BigDecimal before);
    @Query(value = "select p from productsshop.domain.model.Product p where p.seller is not null and p.buyer is not null order by " +
            "p.seller.firstName")
    Product[] findAllByAtLeastOneProductSoldWithTheirBuyer();
}
