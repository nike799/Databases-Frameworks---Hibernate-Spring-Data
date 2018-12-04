package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.towndtos.TownImportDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.service.contract.TownService;
import mostwanted.util.contract.FileUtil;
import mostwanted.util.contract.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class TownServiceImpl implements TownService {
    private final static String TOWNS_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/import/towns.json";
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil,
                           Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return this.fileUtil.readFile(TOWNS_JSON_FILE_PATH);
    }

    @Override
    public String importTowns(String townsFileContent) throws IOException {
        StringBuilder importResult = new StringBuilder();
        Arrays.stream(this.gson.fromJson(this.readTownsJsonFile(), TownImportDto[].class)).
                forEach(townImportDto -> {
                    Town town = this.townRepository.findByName(townImportDto.getName()).
                            orElse(null);
                    if (!this.validationUtil.isValid(townImportDto)) {
                        importResult.append(Constants.INCORRECT_DATA_MESSAGE).
                                append(System.lineSeparator());
                        return;
                    }
                    if (town != null) {
                        importResult.append(Constants.DUPLICATE_DATA_MESSAGE).
                                append(System.lineSeparator());
                        return;
                    }
                    town = this.modelMapper.map(townImportDto, Town.class);
                    this.townRepository.saveAndFlush(town);
                    importResult.append(
                            String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                                    town.getClass().getSimpleName(),
                                    town.getName())).
                            append(System.lineSeparator());
                });
        return importResult.toString().trim();
    }

    @Override
    public String exportRacingTowns() {
        return null;
    }
}
