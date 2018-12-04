package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.districtdtos.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Town;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.service.contract.DistrictService;
import mostwanted.util.contract.FileUtil;
import mostwanted.util.contract.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class DistrictServiceImpl implements DistrictService {
    private final static String DISTRICTS_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/import/districts.json";
    private final DistrictRepository districtRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public DistrictServiceImpl(DistrictRepository districtRepository, TownRepository townRepository, FileUtil fileUtil,
                               Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.districtRepository = districtRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean districtsAreImported() {

        return this.districtRepository.count() > 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return this.fileUtil.readFile(DISTRICTS_JSON_FILE_PATH);
    }

    @Override
    public String importDistricts(String districtsFileContent) throws IOException {
        StringBuilder importResult = new StringBuilder();
        Arrays.stream(this.gson.fromJson(this.readDistrictsJsonFile(), DistrictImportDto[].class)).
                forEach(districtImportDto -> {
                    District district = this.districtRepository.findByName(districtImportDto.getName()).
                            orElse(null);
                    if (!this.validationUtil.isValid(districtImportDto)) {
                        importResult.append(Constants.INCORRECT_DATA_MESSAGE).
                                append(System.lineSeparator());
                        return;
                    } else if (district != null) {
                        importResult.append(Constants.DUPLICATE_DATA_MESSAGE).
                                append(System.lineSeparator());
                        return;
                    }
                    Town town = this.townRepository.findByName(districtImportDto.getTownName()).
                            orElse(null);
                    district = this.modelMapper.map(districtImportDto, District.class);
                    district.setTown(town);
                    this.districtRepository.saveAndFlush(district);
                    importResult.append(
                            String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                                    district.getClass().getSimpleName(),
                                    district.getName())).
                            append(System.lineSeparator());
                });
        return importResult.toString().trim();
    }
}
