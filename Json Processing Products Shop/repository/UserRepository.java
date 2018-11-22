package productsshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import productsshop.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User getFirstById(Long id);

}
