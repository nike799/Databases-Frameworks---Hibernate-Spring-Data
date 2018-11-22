package productsshop.service.contract;

import java.io.IOException;
import java.math.BigDecimal;

public interface ProductService {
    void seedProducts() throws IOException;
    void getAllProductsInASpecifiedPriceRangeWhichHaveNoBuyer(BigDecimal after,BigDecimal before) throws IOException;
}
