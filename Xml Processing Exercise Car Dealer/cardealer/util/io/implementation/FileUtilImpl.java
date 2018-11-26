package cardealer.util.io.implementation;

import cardealer.util.io.contract.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtilImpl implements FileUtil {
    private File file;
    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder;

    @Override
    public String getFileContent(String filePath) throws IOException {
        this.file = new File(filePath);
        this.bufferedReader = new BufferedReader(new FileReader(this.file));
        this.stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
