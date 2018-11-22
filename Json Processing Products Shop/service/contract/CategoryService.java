package productsshop.service.contract;

import java.io.IOException;

public interface CategoryService {
    void seedCategories() throws IOException;
    void getAllCategoriesOrderByCountProducts() throws IOException;
}
