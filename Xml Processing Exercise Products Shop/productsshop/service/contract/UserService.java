package productsshop.service.contract;


import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface UserService {
    void seedUsers() throws IOException, JAXBException;
    void getAllUsersWhoHaveAtLeastOneProductSoldWithBuyer() throws JAXBException;
}
