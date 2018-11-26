package cardealer.service.contract;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CarService {
    void seedData() throws IOException, JAXBException;
void getAllToyotaCarsAndExportToXml() throws JAXBException;
void getAllCarsWithTheirPartsAndExportedToXml() throws JAXBException;
}
