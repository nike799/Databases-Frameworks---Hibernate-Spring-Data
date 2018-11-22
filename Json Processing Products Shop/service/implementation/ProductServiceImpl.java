package productsshop.service.implementation;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productsshop.domain.dto.ProductDto;
import productsshop.domain.dto.ProductInPriceRangeDto;
import productsshop.domain.model.Category;
import productsshop.domain.model.Product;
import productsshop.domain.model.User;
import productsshop.repository.CategoryRepository;
import productsshop.repository.ProductRepository;
import productsshop.repository.UserRepository;
import productsshop.service.contract.ProductService;
import productsshop.web.util.io.contract.FileUtil;
import productsshop.web.util.io.contract.ValidatorUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCT_JSON_PATH_FILE =
            "C:\\Users\\Nike\\Downloads\\JsonProcessingProductsShop\\src\\main\\resources\\files\\products.json";
    private static final String PATH_FILE_PRODUCTS_IN_RANGE_JSON = "C:\\\\Users\\\\Nike\\\\Downloads\\\\JsonProcessingProductsShop\\\\src\\\\main\\\\resources\\\\files\\\\products-in-range.json";
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidatorUtil validator;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository,
                              CategoryRepository categoryRepository, FileUtil fileUtil, Gson gson,
                              ModelMapper mapper, ValidatorUtil validator) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    @Transactional
    public void seedProducts() throws IOException {
        if (this.productRepository.count() > 0) {
            return;
        }
        String productsFileContent = (this.fileUtil.getFileContent(PRODUCT_JSON_PATH_FILE));
        ProductDto[] productDtos = this.gson.fromJson(productsFileContent, ProductDto[].class);
        for (ProductDto dto : productDtos) {
            if (!this.validator.isValid(dto)) {
                this.validator.violations(dto).forEach(violation -> System.out.println(violation.getMessage()));
                continue;
            }
            Product product = this.mapper.map(dto, Product.class);
            User seller = this.getRandomUser();
            product.setSeller(seller);
            Random random = new Random();
            if (random.nextInt() % 11 != 0) {
                User buyer = this.getRandomUser();
                product.setBuyer(buyer);
            }
            Set<Category> categories = this.getRandomCategories();
            product.setCategories(categories);
            this.productRepository.saveAndFlush(product);
        }
    }

    @Override
    public void getAllProductsInASpecifiedPriceRangeWhichHaveNoBuyer(BigDecimal after, BigDecimal before) throws IOException {
        File file = new File(PATH_FILE_PRODUCTS_IN_RANGE_JSON);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("");

        List<Product> products = this.productRepository.findAllByPriceIsBetweenAndBuyerIsNullOrderByPriceAsc(after, before);
        List<ProductInPriceRangeDto> productInPriceRangeDtos =new ArrayList<>();
        products.forEach(product -> {
            ProductInPriceRangeDto productInPriceRangeDto = this.mapper.map(product,ProductInPriceRangeDto.class);
            productInPriceRangeDto.setSeller(product.getSeller().getFirstName()+" " +product.getSeller().getLastName());
            productInPriceRangeDtos.add(productInPriceRangeDto);
        });
        String productsInRangeToJson = this.gson.toJson(productInPriceRangeDtos);
        fileWriter.write(productsInRangeToJson);
        fileWriter.close();
    }

    private User getRandomUser() {
        Random random = new Random();
        int id = random.nextInt((int) this.userRepository.count());
        if (id == 0) {
            id++;
        }
        User user = this.userRepository.getFirstById((long) id);
        return user;
    }

    private Set<Category> getRandomCategories() {
        Random random = new Random();
        Set<Category> categories = new HashSet<>();
        for (int i = 0; i < this.categoryRepository.count() / 10; i++) {
            int id = random.nextInt((int) this.categoryRepository.count());
            if (id == 0) {
                id++;
            }
            Category category = this.categoryRepository.findFirstById((long) id);
            categories.add(category);
        }
        return categories;
    }

    private void setSomeProductsBuyersToNull(Product[] products) {
        Random random = new Random();
        for (int i = 0; i < products.length / 10; i++) {
            int randomBuyerId = random.nextInt(products.length);
            if (randomBuyerId == 0) {
                randomBuyerId++;
            }
            Product product = this.productRepository.getOne((long) randomBuyerId);
            product.setBuyer(null);
            this.productRepository.saveAndFlush(product);
        }
    }
}
