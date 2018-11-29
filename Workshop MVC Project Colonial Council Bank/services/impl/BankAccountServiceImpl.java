package app.ccb.services.impl;

import app.ccb.domain.dtos.BankAccountImportDto;
import app.ccb.domain.dtos.BankAccountImportRootDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Client;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.ClientRepository;
import app.ccb.services.contract.BankAccountService;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.HashSet;
import java.util.Set;


@Service
public class BankAccountServiceImpl implements BankAccountService {
    private final static String BANK_ACCOUNTS_XML_PATH_FILE =
            "C:\\Users\\Nike\\Desktop\\Softuni Projects\\ColonialCouncilBank\\src\\main\\resources\\files\\xml\\bank-accounts.xml";
    private final BankAccountRepository bankAccountRepository;
    private final ClientRepository clientRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, ClientRepository clientRepository, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.bankAccountRepository = bankAccountRepository;
        this.clientRepository = clientRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean bankAccountsAreImported() {
        return this.bankAccountRepository.count() != 0;

    }

    @Override
    public String readBankAccountsXmlFile() throws JAXBException, IOException {
        return this.fileUtil.readFile(BANK_ACCOUNTS_XML_PATH_FILE);
    }

    @Override
    public String importBankAccounts() throws JAXBException, FileNotFoundException {
        StringBuilder result = new StringBuilder();
        JAXBContext context = JAXBContext.newInstance(BankAccountImportRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(BANK_ACCOUNTS_XML_PATH_FILE))));
        BankAccountImportRootDto bankAccountImportRootDto = (BankAccountImportRootDto) unmarshaller.unmarshal(reader);
        for (BankAccountImportDto dto : bankAccountImportRootDto.getBankAccountImportDtos()) {
            if (!this.validationUtil.isValid(dto)) {
                result.append("Incorrect data!").append(System.lineSeparator());
                continue;
            }
            BankAccount entity = new BankAccount();
            Set<Client> clientSet = new HashSet<>(clientRepository.findAll());
            Client client = clientSet.stream().filter(c -> c.getFullName().equals(dto.getClientName())).findAny().orElse(null);

            if (client == null) {
                result.append("Incorrect data!").append(System.lineSeparator());
                continue;
            }
            entity.setAccountNumber(dto.getAccountNumber());
            entity.setBalance(dto.getBalance());
            entity.setClient(client);
            this.bankAccountRepository.saveAndFlush(entity);
            result.append(String.format("Successfully imported Bank Account - %s.",
                    entity.getAccountNumber())).
                    append(System.lineSeparator());
        }
        return result.toString();
    }
}

