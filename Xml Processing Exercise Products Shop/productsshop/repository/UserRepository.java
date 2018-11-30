package productsshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import productsshop.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User getFirstById(Long id);
@Query("select u from productsshop.domain.model.User u join productsshop.domain.model.Product p on u = p.seller where p.seller is not null and p.buyer is not null")
    User[] findAllUsersWhoHaveAtLeastOneProductsoldWitnBuyer();
}
