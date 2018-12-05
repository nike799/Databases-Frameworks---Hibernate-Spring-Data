package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.raceentrydtos.RaceEntryImportRootDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.service.contract.RaceEntryService;
import mostwanted.util.contract.FileUtil;
import mostwanted.util.contract.ValidationUtil;
import mostwanted.util.contract.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Arrays;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {
    private final static String RACE_ENTRIES_XML_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/import/race-entries.xml";
    private final RaceEntryRepository raceEntryRepository;
    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository, CarRepository carRepository, RacerRepository racerRepository, FileUtil fileUtil,
                                Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.raceEntryRepository = raceEntryRepository;
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean raceEntriesAreImported() {
        return this.raceEntryRepository.count() > 0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws IOException {
        return this.fileUtil.readFile(RACE_ENTRIES_XML_FILE_PATH);
    }

    @Override
    public String importRaceEntries() throws IOException, JAXBException {
        StringBuilder importResult = new StringBuilder();
        RaceEntryImportRootDto raceEntryImportRootDto = this.xmlParser.parseXml(RaceEntryImportRootDto.class, RACE_ENTRIES_XML_FILE_PATH);
        Arrays.stream(raceEntryImportRootDto.getRaceEntryImportDtos()).forEach(raceEntryImportDto -> {
            RaceEntry raceEntry = this.modelMapper.map(raceEntryImportDto, RaceEntry.class);
            Car car = this.carRepository.findById(raceEntryImportDto.getCarId()).orElse(null);
            Racer racer = this.racerRepository.findByName(raceEntryImportDto.getRacerName()).orElse(null);
            raceEntry.setCar(car);
            raceEntry.setRacer(racer);
            raceEntry.setRace(null);
            this.raceEntryRepository.saveAndFlush(raceEntry);
            importResult.append(String.format(
                    Constants.SUCCESSFUL_IMPORT_MESSAGE_RACER_OR_RACE_ENTRY,
                    raceEntry.getClass().getSimpleName(),
                    raceEntry.getId())).
                    append(System.lineSeparator());
            System.out.println();
        });
        return importResult.toString().trim();
    }
}
