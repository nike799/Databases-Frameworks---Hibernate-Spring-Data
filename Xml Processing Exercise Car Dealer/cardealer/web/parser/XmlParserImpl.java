package cardealer.web.parser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class XmlParserImpl implements XmlParser {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T parseXml(Class<T> clazz, String pathFile) throws JAXBException, FileNotFoundException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(pathFile))));
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (T) unmarshaller.unmarshal(bufferedReader);
    }
    @Override
    public <T> void exportToXml(T object, Class<T> objectClass, String path) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(objectClass);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(object, new File(path));
    }
}
