package cardealer.service.contract;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface SupplierService {
    void seedData() throws IOException, JAXBException;
void getAllByImporterIsFalseAndExportToXml() throws JAXBException;
}
