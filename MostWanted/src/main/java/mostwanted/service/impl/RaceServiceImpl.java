package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.repository.RaceRepository;
import mostwanted.service.contract.RaceService;
import mostwanted.util.contract.FileUtil;
import mostwanted.util.contract.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RaceServiceImpl implements RaceService {
    private final static String RACES_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/import/races.json";
private final RaceRepository raceRepository;
private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public RaceServiceImpl(RaceRepository raceRepository, FileUtil fileUtil,
                           Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.raceRepository = raceRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean racesAreImported() {
        return this.raceRepository.count()>0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        return this.fileUtil.readFile(RACES_JSON_FILE_PATH);
    }

    @Override
    public String importRaces() {
        return null;
    }
}
