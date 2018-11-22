package productsshop.web.util.io.implementation;

import productsshop.web.util.io.contract.InputReader;

import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleInputReader implements InputReader {
    private BufferedReader bufferedReader;

    @Override
    public String readLine() throws IOException {
        return this.bufferedReader.readLine();
    }
}
