package app.ccb.services.impl;

import app.ccb.domain.dtos.CardImportDto;
import app.ccb.domain.dtos.CardImportRootDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Card;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.CardRepository;
import app.ccb.services.contract.CardService;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

@Service
public class CardServiceImpl implements CardService {
    private final static String CARDS_XML_PATH_FILE =
            "C:\\Users\\Nike\\Desktop\\Softuni Projects\\ColonialCouncilBank\\src\\main\\resources\\files\\xml\\cards.xml";
    private final CardRepository cardRepository;
    private final BankAccountRepository bankAccountRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;


    public CardServiceImpl(CardRepository cardRepository, BankAccountRepository bankAccountRepository, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.cardRepository = cardRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean cardsAreImported() {

        return this.cardRepository.count() != 0;
    }

    @Override
    public String readCardsXmlFile() throws IOException {
        return this.fileUtil.readFile(CARDS_XML_PATH_FILE);
    }

    @Override
    public String importCards() throws JAXBException, FileNotFoundException {
        StringBuilder result = new StringBuilder();
        JAXBContext context = JAXBContext.newInstance(CardImportRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(CARDS_XML_PATH_FILE))));
        CardImportRootDto cardImportRootDtos = (CardImportRootDto) unmarshaller.unmarshal(reader);
        for (CardImportDto dto : cardImportRootDtos.getCardImportDtos()) {
            if (!validationUtil.isValid(dto)) {
                result.append("Incorrect data!").append(System.lineSeparator());
                continue;
            }
            BankAccount bankAccount = this.bankAccountRepository.findByAccountNumber(dto.getAccountNumber()).orElse(null);
            if (bankAccount == null) {
                result.append("Incorrect data!").append(System.lineSeparator());
                continue;
            }
            Card cardEntity = new Card();
            cardEntity.setCardNumber(dto.getCardNumber());
            cardEntity.setCardStatus(dto.getStatus());
            cardEntity.setBankAccount(bankAccount);
            this.cardRepository.saveAndFlush(cardEntity);
            result.append(String.format("Successfully imported Card - %s.",
                            cardEntity.getCardNumber())).
                    append(System.lineSeparator());
        }
        return result.toString();
    }
}
