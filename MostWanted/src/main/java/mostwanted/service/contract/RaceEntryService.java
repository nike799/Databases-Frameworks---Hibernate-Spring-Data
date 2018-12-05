package mostwanted.service.contract;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface RaceEntryService {

    Boolean raceEntriesAreImported();

    String readRaceEntriesXmlFile() throws IOException;

    String importRaceEntries() throws IOException, JAXBException;
}
