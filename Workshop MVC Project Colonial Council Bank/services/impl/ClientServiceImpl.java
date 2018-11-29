package app.ccb.services.impl;

import app.ccb.domain.dtos.ClientImportJsonDto;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.ClientRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.services.contract.ClientService;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final static String CLIENT_CONTENT_PATH_FILE =
            "C:\\Users\\Nike\\Desktop\\Softuni Projects\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\clients.json";
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;


    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, EmployeeRepository employeeRepository, FileUtil fileUtil,
                             ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean clientsAreImported() {
        return this.clientRepository.count() != 0;
    }

    @Override
    public String readClientsJsonFile() throws IOException {
        return this.fileUtil.readFile(CLIENT_CONTENT_PATH_FILE);
    }

    @Override
    public String importClients(String clients) {
        StringBuilder result = new StringBuilder();
        ClientImportJsonDto[] clientImportJsonDtos = this.gson.fromJson(clients, ClientImportJsonDto[].class);
        List<Client> clientList = new ArrayList<>();
        for (ClientImportJsonDto dto : clientImportJsonDtos) {
            if (!this.validationUtil.isValid(dto)) {
                result.append("Incorrect Data!").append(System.lineSeparator());
                continue;
            }
            Client client = new Client();
            String fullNameDto = dto.getFirstName().concat(" ").concat(dto.getLastName());
            Employee appointedEmployee = this.employeeRepository.findByFullName(dto.getAppointedEmployee()).orElse(null);
            if (appointedEmployee == null) {
                result.append("Incorrect Data!").append(System.lineSeparator());
                continue;
            }
            client.setFullName(fullNameDto);
            client.setAge(dto.getAge());
            client.setEmployees(new HashSet<>());
            client.getEmployees().add(appointedEmployee);
            this.clientRepository.saveAndFlush(client);
            clientList.add(client);
            result.append(String.format("Successfully imported Client - %s.", client.getFullName())).append(System.lineSeparator());
        }
        System.out.println();
        return result.toString();
    }

    @Override
    public String exportFamilyGuy() {
        StringBuilder result = new StringBuilder();
        Client client = this.clientRepository.getTopByBankAccount().orElse(null).get(0);
        result.append(System.lineSeparator()).append(String.format("Full Name: %s\n" +
                        "Age: %s\n" +
                        "Bank Account: %s\n",
                client.getFullName(),
                client.getAge(),
                client.getBankAccount().getAccountNumber())).append(System.lineSeparator());
        client.getBankAccount().getCards().forEach(card -> {
            result.append(String.format("Card Number: %s\n" +
                    "Card Status: %s\n", card.getCardNumber(), card.getCardStatus()));
        });
        return result.toString();
    }
}
