package productsshop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import productsshop.service.contract.CategoryService;
import productsshop.service.contract.ProductService;
import productsshop.service.contract.UserService;

import java.io.BufferedReader;
import java.math.BigDecimal;

@Controller
public class ProductsShopController implements CommandLineRunner {
    private final BufferedReader bufferedReader;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public ProductsShopController(BufferedReader bufferedReader, UserService userService,
                                  CategoryService categoryService, ProductService productService) {
        this.bufferedReader = bufferedReader;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();
        while (true) {

            System.out.println("Enter the number of the query:");
            int queryNumber = Integer.parseInt(bufferedReader.readLine());

            switch (queryNumber) {
                case 1:
                    System.out.println("Enter price in rage after:");
                    BigDecimal after = new BigDecimal(bufferedReader.readLine());
                    System.out.println("Enter price in rage before:");
                    BigDecimal before = new BigDecimal(bufferedReader.readLine());
                    this.productService.getAllProductsInASpecifiedPriceRangeWhichHaveNoBuyer(after, before);
                    break;
                case 2:
                    this.userService.getAllUsersByAtLeastOneProductSoldWithTheirBuyer();
                    break;
                case 3:
                    this.categoryService.getAllCategoriesOrderByCountProducts();
                    break;
                case 4:
                    this.userService.getAllUsersByAtLeastOneProductSold();
                    break;
            }
        }

    }
}
