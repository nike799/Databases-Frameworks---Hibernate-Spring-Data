package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.racerdtos.RacerImportDto;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.service.contract.RacerService;
import mostwanted.util.contract.FileUtil;
import mostwanted.util.contract.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class RacerServiceImpl implements RacerService {
    private final static String RACERS_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/import/racers.json";
    private final RacerRepository racerRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public RacerServiceImpl(RacerRepository racerRepository, TownRepository townRepository, FileUtil fileUtil,
                            Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.racerRepository = racerRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean racersAreImported() {
        return this.racerRepository.count() > 0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        return this.fileUtil.readFile(RACERS_JSON_FILE_PATH);
    }

    @Override
    public String importRacers(String racersFileContent) throws IOException {
        StringBuilder importResult = new StringBuilder();
        Arrays.stream(this.gson.fromJson(this.readRacersJsonFile(), RacerImportDto[].class)).
                forEach(racerImportDto -> {
                    if (!this.validationUtil.isValid(racerImportDto)) {
                        importResult.append(Constants.INCORRECT_DATA_MESSAGE).
                                append(System.lineSeparator());
                        return;
                    }
                    Racer racer = this.racerRepository.findByName(racerImportDto.getName()).orElse(null);
                    if (racer != null) {
                        importResult.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                    }
                    Town homeTown = this.townRepository.findByName(racerImportDto.getHomeTown()).orElse(null);
                    racer = this.modelMapper.map(racerImportDto, Racer.class);
                    racer.setHomeTown(homeTown);
                    this.racerRepository.saveAndFlush(racer);
                    importResult.append(
                            String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                                    racer.getClass().getSimpleName(),
                                    racer.getName())).
                            append(System.lineSeparator());
                });
        return importResult.toString().trim();
    }

    @Override
    public String exportRacingCars() {
        return null;
    }
}
