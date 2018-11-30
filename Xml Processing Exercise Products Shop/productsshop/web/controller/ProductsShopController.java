package productsshop.web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import productsshop.service.contract.CategoryService;
import productsshop.service.contract.ProductService;
import productsshop.service.contract.UserService;

@Controller
public class ProductsShopController implements CommandLineRunner {
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public ProductsShopController(UserService userService,
                                  CategoryService categoryService, ProductService productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();

        this.productService.getAllByPriceIsAfterAndPriceIsBeforeAndBuyerIsNullOrderByPriceAsc();
        this.userService.getAllUsersWhoHaveAtLeastOneProductSoldWithBuyer();
        this.categoryService.getAllCategoriesSortedByCountOfProductsDesc();

    }
}
