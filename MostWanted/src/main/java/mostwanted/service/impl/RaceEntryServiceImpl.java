package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.service.contract.RaceEntryService;
import mostwanted.util.contract.FileUtil;
import mostwanted.util.contract.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {
    private final static String RACE_ENTRIES_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/import/race-entries.xml";
    private final RaceEntryRepository raceEntryRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository, FileUtil fileUtil,
                                Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.raceEntryRepository = raceEntryRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean raceEntriesAreImported() {
        return this.raceEntryRepository.count() > 0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws IOException {
        String result = "Zdrasti!";
        return result;
    }

    @Override
    public String importRaceEntries() {
        return null;
    }
}
