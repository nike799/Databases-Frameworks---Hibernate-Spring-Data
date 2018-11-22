package productsshop.web.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class JSONParser {
    private Gson gson;

    public JSONParser() {
        this.gson = new GsonBuilder().create();
    }

    public <T> T importJson(Class<T> clazz, String fileName) throws FileNotFoundException {
        InputStream stream = new FileInputStream(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        return this.gson.fromJson(bufferedReader, clazz);
    }
}
