package cardealer.service.contract;

import javax.xml.bind.JAXBException;

public interface SaleService {
    void seedData();
    void getAllSalesWithAppliedDiscount() throws JAXBException;
}
