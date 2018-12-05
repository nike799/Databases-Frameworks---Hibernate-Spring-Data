package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.racedtos.RaceImportRootDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.service.contract.RaceService;
import mostwanted.util.contract.FileUtil;
import mostwanted.util.contract.ValidationUtil;
import mostwanted.util.contract.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class RaceServiceImpl implements RaceService {
    private final static String RACES_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/import/races.xml";
    private final RaceRepository raceRepository;
    private final DistrictRepository districtRepository;
    private final RaceEntryRepository raceEntryRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public RaceServiceImpl(RaceRepository raceRepository, DistrictRepository districtRepository, RaceEntryRepository raceEntryRepository, FileUtil fileUtil,
                           Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.raceRepository = raceRepository;
        this.districtRepository = districtRepository;
        this.raceEntryRepository = raceEntryRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean racesAreImported() {
        return this.raceRepository.count() > 0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        return this.fileUtil.readFile(RACES_JSON_FILE_PATH);
    }

    @Override
    public String importRaces() throws JAXBException, FileNotFoundException {
        StringBuilder importResult = new StringBuilder();
        Arrays.stream(this.xmlParser.parseXml(RaceImportRootDto.class, RACES_JSON_FILE_PATH).
                getRaceImportDtos()).
                forEach(raceImportDto -> {
                    if (!this.validationUtil.isValid(raceImportDto)) {
                        importResult.append(Constants.INCORRECT_DATA_MESSAGE).
                                append(System.lineSeparator());
                        return;
                    }
                    Race race = new Race();
                    District district = this.districtRepository.findByName(raceImportDto.getDistrictName()).orElse(null);
                    race.setLaps(raceImportDto.getLaps());
                    race.setDistrict(district);
                    Set<RaceEntry> entries = new LinkedHashSet<>();
                    Arrays.stream(raceImportDto.getEntries().getRaceEntryIdDtos()).forEach(raceEntryIdDto -> {
                        RaceEntry raceEntry = this.raceEntryRepository.findById(raceEntryIdDto.getId()).orElse(null);
                        entries.add(raceEntry);
                    });
                    race.setEntries(entries);
                    this.raceRepository.saveAndFlush(race);
                    importResult.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE_RACER_OR_RACE_ENTRY,
                            race.getClass().getSimpleName(),
                            race.getId())).
                            append(System.lineSeparator());
                });

        return importResult.toString().trim();
    }
}
