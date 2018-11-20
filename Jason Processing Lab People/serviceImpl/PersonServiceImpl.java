package app.serviceImpl;

import app.domain.dto.PersonDto;
import app.domain.dto.PhoneNumberDto;
import app.domain.model.Address;
import app.domain.model.Person;
import app.domain.model.PhoneNumber;
import app.repository.PersonRepository;
import app.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;


    @Override
    public void create(PersonDto personDto) {
        Person person = mapPersonDtoToModel(personDto);
        this.personRepository.saveAndFlush(person);
    }

    @Override
    public Person findById(long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public List<Person> findByCountry(String country) {
        return this.personRepository.findByCountry(country);
    }

    private Person mapPersonDtoToModel(PersonDto personDto) {
        Person person = new Person();
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        Address address = new Address();
        address.setCountry(personDto.getAddressDto().getCountry());
        address.setCity(personDto.getAddressDto().getCity());
        address.setStreet(personDto.getAddressDto().getStreet());
        person.setAddress(address);
        for (PhoneNumberDto phonenumberDto : personDto.getPhoneNumbers()) {
            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setNumber(phonenumberDto.getNumber());
            person.getPhoneNumbers().add(phoneNumber);
        }
        return person;
    }
}
