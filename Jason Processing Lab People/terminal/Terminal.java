package app.terminal;

import app.domain.dto.PersonDto;
import app.io.JsonParser;
import app.service.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Terminal implements CommandLineRunner {


    private final PersonService personService;
    private final JsonParser parser;

    public Terminal(PersonService personService) {
        this.personService = personService;
        this.parser = new JsonParser();
    }


    @Override
    public void run(String... strings) throws Exception {
        PersonDto[] personDto = this.parser.importJson(PersonDto[].class,
                "C:\\Users\\Nike\\Downloads\\JasonProcessingLabPeople\\src\\main\\resources\\people.json");
        Arrays.stream(personDto).forEach(this.personService::create);
    }
}
