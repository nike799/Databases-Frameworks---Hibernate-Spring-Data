package bookshopsystemapp.util;

import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
public class InputReader {
    private BufferedReader bufferedReader;

    public InputReader() {
        this.bufferedReader =new BufferedReader(new InputStreamReader(System.in));
    }
    public String readLine() throws IOException {
        return this.bufferedReader.readLine();
    }
}
