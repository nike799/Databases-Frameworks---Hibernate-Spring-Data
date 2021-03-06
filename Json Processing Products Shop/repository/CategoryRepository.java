package productsshop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import productsshop.domain.model.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findFirstById(Long id);

    @Query("select c from Category c order by c.products.size asc")
    List<Category> getAllCategoriesOrderByCountProducts();
}
