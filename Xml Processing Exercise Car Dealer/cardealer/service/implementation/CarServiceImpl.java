package cardealer.service.implementation;

import cardealer.domain.dto.cardto.*;
import cardealer.domain.dto.partdto.PartWithNameAndPriceExportDto;
import cardealer.domain.model.Car;
import cardealer.domain.model.Part;
import cardealer.repository.CarRepository;
import cardealer.repository.PartRepository;
import cardealer.repository.SupplierRepository;
import cardealer.service.contract.CarService;
import cardealer.web.constant.PathFileConstant;
import cardealer.web.parser.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository,
                          SupplierRepository supplierRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedData() throws IOException, JAXBException {
        if (this.carRepository.count() > 1) {
            return;
        }
        CarImporterRootDto carImporterRootDto =
                this.xmlParser.parseXml(CarImporterRootDto.class, PathFileConstant.CARS_XML_PATH_FILE);
        Car[] cars = this.modelMapper.map(carImporterRootDto.getCars(), Car[].class);
        Arrays.stream(cars).forEach(car -> {
            car.setParts(getRandomParts());
            this.carRepository.saveAndFlush(car);
        });
    }

    @Override
    public void getAllToyotaCarsAndExportToXml() throws JAXBException {
        Car[] cars = this.carRepository.findAllToyotaOrderByModel("Toyota");
        CarExportDto[] carExportDtos = this.modelMapper.map(cars, CarExportDto[].class);
        CarExportRootDto carExportRootDto = new CarExportRootDto();
        carExportRootDto.setCarExportDtos(carExportDtos);
        this.xmlParser.exportToXml(carExportRootDto, CarExportRootDto.class, PathFileConstant.TOYOTA_CARS_XML_PATH_FILE);
    }

    @Override
    public void getAllCarsWithTheirPartsAndExportedToXml() throws JAXBException {
        Car[] cars = this.carRepository.findAll().toArray(new Car[0]);
        CarWithPartsExportDto[] carWithPartsExportDtos = this.modelMapper.map(cars, CarWithPartsExportDto[].class);
        for (int i = 0; i < cars.length; i++) {
            PartWithNameAndPriceExportDto[] partWithNameAndPriceExportDtos =
                    this.modelMapper.map(cars[i].getParts(), PartWithNameAndPriceExportDto[].class);
            carWithPartsExportDtos[i].setPartWithNameAndPriceExportDtos(partWithNameAndPriceExportDtos);
        }
        CarWithPartsExportRootDto carWithPartsExportRootDto = new CarWithPartsExportRootDto();
        carWithPartsExportRootDto.setCarWithPartsExportDtos(carWithPartsExportDtos);
        this.xmlParser.exportToXml(carWithPartsExportRootDto, CarWithPartsExportRootDto.class, PathFileConstant.CARS_AND_PARTS_XML_PATH_FILE);
    }

    private List<Part> getRandomParts() {
        Random random = new Random();
        List<Part> parts = new ArrayList<>();
        int low = 10;
        int high = 20;
        int count = random.nextInt(high - low) + low;
        for (int i = 0; i < count; i++) {
            int randomId = random.nextInt((int) this.partRepository.count() - 1) + 1;
            Part part = this.partRepository.findById((long) randomId).orElse(null);
            parts.add(part);
        }
        return parts;
    }
}
