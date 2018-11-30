package productsshop.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productsshop.domain.dto.categorydto.CategoryByProductsExportDto;
import productsshop.domain.dto.categorydto.CategoryByProductsExportRootDto;
import productsshop.domain.dto.categorydto.CategoryImportRootDto;
import productsshop.domain.model.Category;
import productsshop.domain.model.Product;
import productsshop.repository.CategoryRepository;
import productsshop.service.contract.CategoryService;
import productsshop.util.contract.FileUtil;
import productsshop.util.contract.ValidatorUtil;
import productsshop.web.parser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final static String CATEGORIES_XML_PATH_FILE =
            "C:\\Users\\Nike\\Desktop\\Softuni Projects\\XmlProcessingProductsShop\\src\\main\\resources\\files\\import\\categories.xml";
    private final static String CATEGORIES_BY_PRODUCTS_XML_PATH_FILE =
            "C:\\Users\\Nike\\Desktop\\Softuni Projects\\XmlProcessingProductsShop\\src\\main\\resources\\files\\export\\categories-by-products.xml";

    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil,
                               XmlParser xmlParser, ModelMapper mapper, ValidatorUtil validatorUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public void seedCategories() throws IOException, JAXBException {
        if (this.categoryRepository.count() > 0) {
            return;
        }
        CategoryImportRootDto categoryImportRootDto = this.xmlParser.parseXml(CategoryImportRootDto.class, CATEGORIES_XML_PATH_FILE);
        Category[] categories = this.mapper.map(categoryImportRootDto.getCategoryImportDtos(), Category[].class);
        Arrays.stream(categories).forEach(this.categoryRepository::saveAndFlush);
    }

    @Override
    public void getAllCategoriesSortedByCountOfProductsDesc() throws JAXBException {
        Category[] categories = this.categoryRepository.findAllOrOrderByProducts();
        CategoryByProductsExportRootDto categoryByProductsExportRootDto = new CategoryByProductsExportRootDto();
        CategoryByProductsExportDto[] categoryByProductsExportDtos = this.mapper.map(categories, CategoryByProductsExportDto[].class);
        int index = 0;
        for (Category category : categories) {
            BigDecimal[] values = getAveragePriceAndTotalRevenue(category.getProducts());
            categoryByProductsExportDtos[index].setProductsCount(category.getProducts().size());
            categoryByProductsExportDtos[index].setAveragePrice(values[0]);
            categoryByProductsExportDtos[index].setTotalRevenue(values[1]);
            index++;
        }
        categoryByProductsExportRootDto.setCategoryByProductsExportDtos(categoryByProductsExportDtos);
        this.xmlParser.exportToXml(categoryByProductsExportRootDto,CategoryByProductsExportRootDto.class,CATEGORIES_BY_PRODUCTS_XML_PATH_FILE);
    }

    private BigDecimal[] getAveragePriceAndTotalRevenue(Set<Product> products) {
        BigDecimal[] values = new BigDecimal[2];
        BigDecimal averagePrice=BigDecimal.ZERO;
        BigDecimal totalRevenue=BigDecimal.ZERO;
        if (products.size()!=0) {
            totalRevenue = products.stream().map(Product::getPrice).reduce(BigDecimal::add).get();
            averagePrice = totalRevenue.divide(BigDecimal.valueOf(products.size()), RoundingMode.HALF_DOWN);
        }
        values[0] = averagePrice;
        values[1] = totalRevenue;
        return values;
    }
}
