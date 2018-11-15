package bookshopsystemapp.util;


import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Component
public class ConsoleReaderImpl implements ConsoleReader {

    private BufferedReader bufferedReader;

    public ConsoleReaderImpl() {
        this.bufferedReader =new BufferedReader(new InputStreamReader(System.in));
    }
    @Override
    public String readLine() throws IOException {
        return this.bufferedReader.readLine();
    }
}
