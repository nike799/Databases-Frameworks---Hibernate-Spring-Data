package app.ccb.services.impl;

import app.ccb.domain.dtos.BranchImportJsonDto;
import app.ccb.domain.entities.Branch;
import app.ccb.repositories.BranchRepository;
import app.ccb.services.contract.BranchService;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BranchServiceImpl implements BranchService {
    private final static String BRANCH_CONTENT_JSON_PATH_FILE =
            "C:\\Users\\Nike\\Desktop\\Softuni Projects\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\branches.json";
    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, FileUtil fileUtil, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean branchesAreImported() {

        return this.branchRepository.count() != 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return this.fileUtil.readFile(BRANCH_CONTENT_JSON_PATH_FILE);
    }

    @Override
    public String importBranches(String branchesJson) {
        StringBuilder result = new StringBuilder();
        BranchImportJsonDto[] branchImportJsonDtos = this.gson.fromJson(branchesJson,BranchImportJsonDto[].class);
        for (BranchImportJsonDto dto:branchImportJsonDtos) {
            if (!this.validationUtil.isValid(dto)){
                result.append("Incorrect Data!").append(System.lineSeparator());
                continue;
            }
            Branch branchEntity = this.modelMapper.map(dto,Branch.class);
            this.branchRepository.saveAndFlush(branchEntity);
            result.append(String.format("Successfully imported Branch – %s",branchEntity.getName()));
        }
        return result.toString();
    }
}
