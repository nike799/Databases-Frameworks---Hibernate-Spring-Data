package productsshop.service.contract;


import java.io.IOException;

public interface UserService {
    void seedUsers() throws IOException;
    void getAllUsersByAtLeastOneProductSoldWithTheirBuyer() throws IOException;
    void getAllUsersByAtLeastOneProductSold() throws IOException;
}
