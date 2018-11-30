package productsshop.service.contract;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CategoryService {
    void seedCategories() throws IOException, JAXBException;
    void getAllCategoriesSortedByCountOfProductsDesc() throws JAXBException;
}
