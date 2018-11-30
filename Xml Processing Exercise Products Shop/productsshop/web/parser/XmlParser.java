package productsshop.web.parser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XmlParser {
    <T> T parseXml(Class<T> clazz, String pathFile) throws JAXBException, FileNotFoundException;
    <T> void exportToXml(T object, Class<T> objectClass, String path) throws JAXBException;
}
