package app.ccb.services.contract;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface CardService {

    Boolean cardsAreImported();

    String readCardsXmlFile() throws IOException;

    String importCards() throws JAXBException, FileNotFoundException;
}
