package cardealer.service.implementation;

import cardealer.domain.dto.cardto.CustomerTotalSalesExportDto;
import cardealer.domain.dto.cardto.CustomerTotalSalesExportRootDto;
import cardealer.domain.dto.customerdto.CustomerExportDto;
import cardealer.domain.dto.customerdto.CustomerExportRootDto;
import cardealer.domain.dto.customerdto.CustomerImporterRootDto;
import cardealer.domain.model.Customer;
import cardealer.repository.CustomerRepository;
import cardealer.service.contract.CustomerService;
import cardealer.web.constant.PathFileConstant;
import cardealer.web.parser.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;


    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               ModelMapper modelMapper, XmlParser xmlParser) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedData() throws IOException, JAXBException {
        if (this.customerRepository.count() > 0) {
            return;
        }
        CustomerImporterRootDto customerImporterRootDto =
                this.xmlParser.parseXml(CustomerImporterRootDto.class, PathFileConstant.CUSTOMER_XML_PATH_FILE);
        Customer[] customers = this.modelMapper.map(customerImporterRootDto.getCustomers(), Customer[].class);
        for (int i = 0; i < customers.length; i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate bithDate = LocalDate.parse(customerImporterRootDto.getCustomers()[i].getBirthDate(), formatter);
            customers[i].setDateOfBirth(bithDate);
            this.customerRepository.saveAndFlush(customers[i]);
        }
    }

    @Override
    public void getAllCustomersOrderedByBirthDay() throws JAXBException {
        Customer[] customers = this.customerRepository.findAllOrOrderByBirthDateAscYoungDriverIsFalse();
        CustomerExportDto[] customerExportDto = this.modelMapper.map(customers, CustomerExportDto[].class);
        CustomerExportRootDto customerExportRootDto = new CustomerExportRootDto();
        customerExportRootDto.setCustomerExportDtos(customerExportDto);
        this.xmlParser.exportToXml(customerExportRootDto, CustomerExportRootDto.class, PathFileConstant.ORDERED_CUSTOMER_EXPORT_PATH_FILE);
    }

    @Override
    public void getAllCustomersWithTotalSales() throws JAXBException {
        Object[][] matrix = this.customerRepository.getAllCustomerWithBoughtCars();
        CustomerTotalSalesExportRootDto customerTotalSalesExportRootDtoDto = new CustomerTotalSalesExportRootDto();
        customerTotalSalesExportRootDtoDto.setCustomerTotalSalesExportDtos(new LinkedList<>());
        for (Object[] array : matrix) {
            String name = (String) array[0];
            Integer boughtCars = Integer.parseInt(String.valueOf(array[1]));
            BigDecimal totalSpentMoney = (BigDecimal) array[2];
            CustomerTotalSalesExportDto customerTotalSalesExportDto = new CustomerTotalSalesExportDto();
            customerTotalSalesExportDto.setFullName(name);
            customerTotalSalesExportDto.setBoughtCars(boughtCars);
            customerTotalSalesExportDto.setSpentMoney(totalSpentMoney);
            customerTotalSalesExportRootDtoDto.getCustomerTotalSalesExportDtos().add(customerTotalSalesExportDto);
        }
        List<CustomerTotalSalesExportDto> sorted = customerTotalSalesExportRootDtoDto.getCustomerTotalSalesExportDtos().
                stream().sorted((customer1, customer2) -> {
            if (customer2.getSpentMoney().compareTo(customer1.getSpentMoney()) != 0) {
                return customer2.getSpentMoney().compareTo(customer1.getSpentMoney());
            } else {
                return customer2.getBoughtCars().compareTo(customer1.getBoughtCars());
            }
        }).collect(Collectors.toList());
        customerTotalSalesExportRootDtoDto.setCustomerTotalSalesExportDtos(sorted);
        this.xmlParser.exportToXml(customerTotalSalesExportRootDtoDto,CustomerTotalSalesExportRootDto.class,PathFileConstant.CUSTOMERS_TOTAL_SALES_XML_PATH_FILE);
    }
}
