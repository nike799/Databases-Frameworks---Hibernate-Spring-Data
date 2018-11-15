package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Category;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final static String CATEGORY_FILE_PATH =
            "C:\\Users\\Nike\\IdeaProjects\\SpringDataIntroBookshopSystem\\src\\main\\resources\\files\\categories.txt";
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() != 0){
            return;
        }
        String[] categoryFileContent = this.fileUtil.getFileContent(CATEGORY_FILE_PATH);
        for (String line: categoryFileContent) {
            Category lineParams = new Category(line);
            this.categoryRepository.saveAndFlush(lineParams);
        }
    }
}
