package mostwanted.service.contract;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface RaceService {

    Boolean racesAreImported();

    String readRacesXmlFile() throws IOException;

    String importRaces() throws JAXBException, FileNotFoundException;
}
