package mostwanted.service.contract;

import java.io.IOException;

public interface RacerService {

    Boolean racersAreImported();

    String readRacersJsonFile() throws IOException;

    String importRacers(String racersFileContent) throws IOException;

    String exportRacingCars();
}
