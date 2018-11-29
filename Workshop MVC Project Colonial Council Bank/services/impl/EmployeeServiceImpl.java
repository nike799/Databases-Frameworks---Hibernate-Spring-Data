package app.ccb.services.impl;

import app.ccb.domain.dtos.EmployeeImportJsonDto;
import app.ccb.domain.entities.Branch;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.BranchRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.services.contract.EmployeeService;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final static String EMPLOYEE_CONTENT_JSON_PATH_FILE =
            "C:\\Users\\Nike\\Desktop\\Softuni Projects\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\employees.json";
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BranchRepository branchRepository, FileUtil fileUtil,
                               ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() != 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEE_CONTENT_JSON_PATH_FILE);
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder result = new StringBuilder();
        EmployeeImportJsonDto[] employeeImportJsonDtos = this.gson.fromJson(employees, EmployeeImportJsonDto[].class);
        for (EmployeeImportJsonDto dto : employeeImportJsonDtos) {
            if (!this.validationUtil.isValid(dto)) {
                result.append("Incorrect Data!").append(System.lineSeparator());
                continue;
            }
            String branchName = dto.getBranchName();
            Branch branch = this.branchRepository.findByNameIsLike(dto.getBranchName()).orElse(null);
            if (branch == null) {
                continue;
            }
            Employee entity = new Employee();
            entity.setFirstName(dto.getName().split("\\s+")[0]);
            entity.setLastName(dto.getName().split("\\s+")[1]);
            entity.setSalary(new BigDecimal(dto.getSalary()));
            entity.setStartedOn(LocalDate.parse(dto.getStartedOn()));
            entity.setBranch(branch);
            this.employeeRepository.saveAndFlush(entity);
            result.append(String.format("Successfully imported Employee - %s %s.", entity.getFirstName(), entity.getLastName()));
        }
        return result.toString();
    }

    @Override
    public String exportTopEmployees() {
        StringBuilder result = new StringBuilder();
        List<Employee> employees = this.employeeRepository.findAllByClientsIsNotNullOrderByClientsDescIdAsc().orElse(null);
        if (employees == null) {
            return result.append("Incorrect Data!").append(System.lineSeparator()).toString();
        }
        for (Employee employee : employees) {
            result.append(System.lineSeparator()).
                    append(String.format("Full name: %s\n" +
                            "Salary: %s\n" +
                            "Started on: %s\n" +
                            "Clients:\n",
                    employee.getFirstName().concat(" ").concat(employee.getLastName()),
                    employee.getSalary(),
                    employee.getStartedOn()));
            employee.getClients().forEach(client -> {
                result.append(client.getFullName()).
                        append(System.lineSeparator());
            });
        }
        return result.toString();
    }
}
