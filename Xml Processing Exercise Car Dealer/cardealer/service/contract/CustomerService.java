package cardealer.service.contract;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CustomerService {
    void seedData() throws IOException, JAXBException;
void getAllCustomersOrderedByBirthDay() throws JAXBException;
void getAllCustomersWithTotalSales() throws JAXBException;
}
