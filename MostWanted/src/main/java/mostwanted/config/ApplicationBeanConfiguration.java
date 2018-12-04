package mostwanted.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mostwanted.util.contract.FileUtil;
import mostwanted.util.contract.ValidationUtil;
import mostwanted.util.contract.XmlParser;
import mostwanted.util.impl.FileUtilImpl;
import mostwanted.util.impl.ValidationUtilImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public FileUtil fileUtil() {
        return new FileUtilImpl();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }

    @Bean
    public XmlParser xmlParser() {
        // TODO : Implement me
        return null;
        //return new XmlParserImpl();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
