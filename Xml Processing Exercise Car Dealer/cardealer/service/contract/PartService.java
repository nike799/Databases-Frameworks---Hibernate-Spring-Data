package cardealer.service.contract;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface PartService {
    void seedData() throws IOException, JAXBException;
}
