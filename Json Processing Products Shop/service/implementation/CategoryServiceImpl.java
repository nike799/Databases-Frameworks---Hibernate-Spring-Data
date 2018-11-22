package productsshop.service.implementation;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productsshop.domain.dto.CategoryDetailInfoDto;
import productsshop.domain.dto.CategoryDto;
import productsshop.domain.model.Category;
import productsshop.domain.model.Product;
import productsshop.repository.CategoryRepository;
import productsshop.service.contract.CategoryService;
import productsshop.web.util.io.contract.FileUtil;
import productsshop.web.util.io.contract.ValidatorUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String CATEGORY_JSON_PATH_FILE =
            "C:\\Users\\Nike\\Downloads\\JsonProcessingProductsShop\\src\\main\\resources\\files\\categories.json";
    private static final String FILE_PATH =
            "C:\\\\Users\\\\Nike\\\\Downloads\\\\JsonProcessingProductsShop\\\\src\\\\main\\\\resources\\\\files\\\\categories-by-products.json";
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               FileUtil fileUtil, Gson gson, ModelMapper mapper, ValidatorUtil validatorUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() > 0) {
            return;
        }
        String categoryFileContent = this.fileUtil.getFileContent(CATEGORY_JSON_PATH_FILE);
        CategoryDto[] categoryDtos = this.gson.fromJson(categoryFileContent, CategoryDto[].class);
        for (CategoryDto dto : categoryDtos) {
            if (!this.validatorUtil.isValid(dto)) {
                this.validatorUtil.violations(dto).
                        forEach(violation -> System.out.println(violation.getMessage()));
                continue;
            }
            Category entity = this.mapper.map(dto, Category.class);
            this.categoryRepository.saveAndFlush(entity);
        }

    }

    @Override
    public void getAllCategoriesOrderByCountProducts() throws IOException {
        File file = new File(FILE_PATH);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("");
        List<Category> entities = this.categoryRepository.getAllCategoriesOrderByCountProducts();
        List<CategoryDetailInfoDto> categoryDetailInfoDtos = new LinkedList<>();
        entities.forEach(e -> {
            CategoryDetailInfoDto categoryDetailInfoDto = new CategoryDetailInfoDto();
            categoryDetailInfoDto.setCategory(e.getName());
            categoryDetailInfoDto.setProductsCount(e.getProducts().size());
            BigDecimal totalRevenue = BigDecimal.ZERO;
            BigDecimal avrPrice = BigDecimal.ZERO;
            if (e.getProducts().size() > 0) {
                totalRevenue = e.getProducts().stream().map(Product::getPrice).reduce(BigDecimal::add).get();
                BigDecimal count = BigDecimal.valueOf(e.getProducts().size());
                avrPrice = totalRevenue.divide(count,2, RoundingMode.HALF_EVEN);
            }
            categoryDetailInfoDto.setAveragePrice(avrPrice);
            categoryDetailInfoDto.setTotalRevenue(totalRevenue);
            categoryDetailInfoDtos.add(categoryDetailInfoDto);
        });
        String  categoryDetailInfoToJson = this.gson.toJson(categoryDetailInfoDtos);
        fileWriter.write(categoryDetailInfoToJson);
        fileWriter.close();
        System.out.println();
    }

}
