package productsshop.util.implementation;

import productsshop.util.contract.InputReader;

import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleInputReader implements InputReader {
    private BufferedReader bufferedReader;

    @Override
    public String readLine() throws IOException {
        return this.bufferedReader.readLine();
    }
}
