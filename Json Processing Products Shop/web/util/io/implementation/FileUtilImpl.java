package productsshop.web.util.io.implementation;

import org.springframework.stereotype.Component;
import productsshop.web.util.io.contract.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
@Component
public class FileUtilImpl implements FileUtil {

    @Override
    public String getFileContent(String filePath) throws IOException {
        File file = new File(filePath);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        StringBuilder lines = new StringBuilder();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.append(line).append(System.lineSeparator());
        }
        return lines.toString();
    }
}
