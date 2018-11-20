package app.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;


public class JsonParser {
    private Gson gson;

    public JsonParser() {
        this.gson = new GsonBuilder().create();
    }

    public <T> T importJson(Class<T> clazz, String fileName) throws FileNotFoundException {
        InputStream stream = new FileInputStream(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        return this.gson.fromJson(bufferedReader, clazz);
    }
}
