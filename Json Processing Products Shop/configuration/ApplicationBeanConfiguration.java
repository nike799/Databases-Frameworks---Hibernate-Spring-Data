package productsshop.configuration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import productsshop.web.util.io.contract.FileUtil;
import productsshop.web.util.io.contract.InputReader;
import productsshop.web.util.io.contract.ValidatorUtil;
import productsshop.web.util.io.implementation.FileUtilImpl;
import productsshop.web.util.io.implementation.ValidatorUtilImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public FileUtil fileUtil(){
        return new FileUtilImpl();
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public Gson gson(){
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }
    @Bean
    public ValidatorUtil validatorUtil(){
        return new ValidatorUtilImpl();
    }
    @Bean
    public BufferedReader bufferedReader(){
      return  new BufferedReader(new InputStreamReader(System.in));
    }

}
