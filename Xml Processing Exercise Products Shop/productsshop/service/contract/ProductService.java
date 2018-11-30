package productsshop.service.contract;
import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface ProductService {
    void seedProducts() throws IOException, JAXBException;
    void getAllByPriceIsAfterAndPriceIsBeforeAndBuyerIsNullOrderByPriceAsc() throws JAXBException;
}
