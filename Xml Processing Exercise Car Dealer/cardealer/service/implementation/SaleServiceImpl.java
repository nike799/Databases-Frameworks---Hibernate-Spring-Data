package cardealer.service.implementation;

import cardealer.domain.dto.cardto.CarWithoutIdExportDto;
import cardealer.domain.dto.saledto.SaleWithAppliedDiscountExportDto;
import cardealer.domain.dto.saledto.SaleWithAppliedDiscountExportRootDto;
import cardealer.domain.model.Car;
import cardealer.domain.model.Customer;
import cardealer.domain.model.Sale;
import cardealer.repository.CarRepository;
import cardealer.repository.CustomerRepository;
import cardealer.repository.SaleRepository;
import cardealer.service.contract.SaleService;
import cardealer.web.constant.PathFileConstant;
import cardealer.web.parser.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.Arrays.sort;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final XmlParser xmlParser;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository,
                           CustomerRepository customerRepository, XmlParser xmlParser) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedData() {
        if (this.saleRepository.count() > 0) {
            return;
        }
        Random random = new Random();

        List<Double> discounts = asList(0.0, 5.0, 10.0, 15.0, 20.0, 30.0, 40.0, 50.0);
        for (int i = 0; i < 50; i++) {
            Sale sale = new Sale();
            int carId = random.nextInt((int) (carRepository.count() - 1)) + 1;
            int customerId = random.nextInt((int) (customerRepository.count() - 1)) + 1;
            int discountIndex = random.nextInt(discounts.size() - 1);
            if (discountIndex < 0) {
                discountIndex = 0;
            }
            Car car = this.carRepository.findById((long) carId).orElse(null);
            Customer customer = customerRepository.findById((long) customerId).orElse(null);
            double discount = discounts.get(discountIndex);
            sale.setCar(car);
            sale.setCustomer(customer);

            if (customer.isYoungDriver()){
                discount+= 5;
            }
            sale.setDiscount(discount);
            saleRepository.saveAndFlush(sale);
        }
    }

    @Override
    public void getAllSalesWithAppliedDiscount() throws JAXBException {
        Object[][] sales = this.saleRepository.findAllSalesWithAppliedDiscount();
        SaleWithAppliedDiscountExportRootDto saleWithAppliedDiscountExportRootDto = new SaleWithAppliedDiscountExportRootDto();
        saleWithAppliedDiscountExportRootDto.setSaleWithAppliedDiscountExportDtos(new LinkedList<>());
        for (Object[] obj:sales) {
            SaleWithAppliedDiscountExportDto saleWithAppliedDiscountExportDto = new SaleWithAppliedDiscountExportDto();
            String carMake = String.valueOf(obj[0]);
            String carModel = String.valueOf(obj[1]);
            String carTravelledDistance = String.valueOf(obj[2]);
            String customerName = String.valueOf(obj[3]);
            String discount = String.valueOf(obj[4]);
            String price = String.valueOf(obj[5]);
            String priceWithDiscount = String.valueOf(obj[6]);
            CarWithoutIdExportDto carWithoutIdExportDto = new CarWithoutIdExportDto();
            carWithoutIdExportDto.setMake(carMake);
            carWithoutIdExportDto.setModel(carModel);
            carWithoutIdExportDto.setTravelledDistance(Long.parseLong(carTravelledDistance));
            saleWithAppliedDiscountExportDto.setCarExportDto(carWithoutIdExportDto);
            saleWithAppliedDiscountExportDto.setName(customerName);
            saleWithAppliedDiscountExportDto.setDiscount(Double.parseDouble(discount));
            saleWithAppliedDiscountExportDto.setPrice(new BigDecimal(price));
            saleWithAppliedDiscountExportDto.setPriceWithDiscount(new BigDecimal(priceWithDiscount));
            saleWithAppliedDiscountExportRootDto.getSaleWithAppliedDiscountExportDtos().add(saleWithAppliedDiscountExportDto);
        }
this.xmlParser.exportToXml(saleWithAppliedDiscountExportRootDto,SaleWithAppliedDiscountExportRootDto.class, PathFileConstant.SALES_DISCOUNTS_XML_PATH_FILE);
    }
}
