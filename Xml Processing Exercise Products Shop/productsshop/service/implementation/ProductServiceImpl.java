package productsshop.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productsshop.domain.dto.productdto.ProductExportDto;
import productsshop.domain.dto.productdto.ProductExportRootDto;
import productsshop.domain.dto.productdto.ProductImportRootDto;
import productsshop.domain.model.Category;
import productsshop.domain.model.Product;
import productsshop.domain.model.User;
import productsshop.repository.CategoryRepository;
import productsshop.repository.ProductRepository;
import productsshop.repository.UserRepository;
import productsshop.service.contract.ProductService;
import productsshop.util.contract.FileUtil;
import productsshop.util.contract.ValidatorUtil;
import productsshop.web.parser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCTS_XML_PATH_FILE =
            "C:\\Users\\Nike\\Desktop\\Softuni Projects\\XmlProcessingProductsShop\\src\\main\\resources\\files\\import\\products.xml";
    private final static String PRODUCTS_IN_RANGE_XML_PATH_FILE =
            "C:\\Users\\Nike\\Desktop\\Softuni Projects\\XmlProcessingProductsShop\\src\\main\\resources\\files\\export\\products-in-range.xml";
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final ValidatorUtil validator;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository,
                              CategoryRepository categoryRepository, FileUtil fileUtil,
                              XmlParser xmlParser, ModelMapper mapper, ValidatorUtil validator) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    @Transactional
    public void seedProducts() throws IOException, JAXBException {
        if (this.productRepository.count() > 0) {
            return;
        }
        ProductImportRootDto productImportRootDto = this.xmlParser.parseXml(ProductImportRootDto.class, PRODUCTS_XML_PATH_FILE);
        Product[] products = this.mapper.map(productImportRootDto.getProductImportDtos(), Product[].class);
        Arrays.stream(products).forEach(product -> {
            product.setBuyer(getRandomBuyer());
            product.setSeller(getRandomSeller());
            product.setCategories(getRandomCategories());
            this.productRepository.saveAndFlush(product);
        });
    }

    @Override
    public void getAllByPriceIsAfterAndPriceIsBeforeAndBuyerIsNullOrderByPriceAsc() throws JAXBException {
        BigDecimal after = new BigDecimal("500");
        BigDecimal before = new BigDecimal("1000");
        Product[] products = this.productRepository.
                findAllByPriceIsBetweenAndBuyerIsNullOrderByPriceAsc(after,before);
        ProductExportRootDto productExportRootDto = new ProductExportRootDto();
        ProductExportDto[] productExportDtos = this.mapper.map(products,ProductExportDto[].class);
        int index = 0;
        for (Product product:products) {
            String firstName;
            String lastName;
            if(product.getSeller().getFirstName()==null){
                firstName = "";
                lastName = product.getSeller().getLastName();
            }else {
                firstName = product.getSeller().getFirstName();
                lastName = product.getSeller().getLastName();
            }
            productExportDtos[index].setSeller(
                    firstName.concat(" ").concat(lastName));
            index++;
        }
        productExportRootDto.setProductExportDtos(productExportDtos);
        this.xmlParser.exportToXml(productExportRootDto,ProductExportRootDto.class,PRODUCTS_IN_RANGE_XML_PATH_FILE);
    }


    private User getRandomBuyer() {
        Random random = new Random();
        int id = random.nextInt((int) this.userRepository.count() - 1) + 1;
        if (id % 7 == 0) {
            return null;
        }
        return this.userRepository.getFirstById((long) id);
    }

    private User getRandomSeller() {
        Random random = new Random();
        int id = random.nextInt((int) this.userRepository.count() - 1) + 1;
        return this.userRepository.getFirstById((long) id);
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
            Product product = this.productRepository.getOne((long) randomBuyerId);
            product.setBuyer(null);
            this.productRepository.saveAndFlush(product);
        }
    }
}
