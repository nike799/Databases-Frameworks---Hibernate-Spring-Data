package cardealer.configuration;

import cardealer.web.parser.XmlParser;
import cardealer.web.parser.XmlParserImpl;
import cardealer.util.io.contract.FileUtil;
import cardealer.util.io.contract.InputReader;
import cardealer.util.io.contract.ValidatorUtil;
import cardealer.util.io.implementation.ConsoleInputReader;
import cardealer.util.io.implementation.FileUtilImpl;
import cardealer.util.io.implementation.ValidatorUtilImpl;
import cardealer.util.timeadapter.LocalDateAdapter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public FileUtil fileUtil() { return new FileUtilImpl();
    }
    @Bean
    public XmlParser xmlParser() { return new XmlParserImpl();
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ValidatorUtil validatorUtil() {
        return new ValidatorUtilImpl();
    }

    @Bean
    public InputReader inputReader() {
        return new ConsoleInputReader();
    }
    @Bean
    public LocalDateAdapter localDateAdapter(){
        return new LocalDateAdapter();
    }

}
