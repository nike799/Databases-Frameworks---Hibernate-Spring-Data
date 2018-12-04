package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.cardtos.CarImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.service.contract.CarService;
import mostwanted.util.contract.FileUtil;
import mostwanted.util.contract.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class CarServiceImpl implements CarService {
    private final static String CARS_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/import/cars.json";
    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CarServiceImpl(CarRepository carRepository, RacerRepository racerRepository, FileUtil fileUtil,
                          Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean carsAreImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return this.fileUtil.readFile(CARS_JSON_FILE_PATH);
    }

    @Override
    public String importCars(String carsFileContent) throws IOException {
        StringBuilder importResult = new StringBuilder();
        Arrays.stream(this.gson.fromJson(this.readCarsJsonFile(), CarImportDto[].class)).
                forEach(carImportDto -> {
                    if (!this.validationUtil.isValid(carImportDto)) {
                        importResult.append(Constants.INCORRECT_DATA_MESSAGE).
                                append(System.lineSeparator());
                        return;
                    }
                    String brand = carImportDto.getBrand();
                    String model = carImportDto.getModel();
                    Integer yearOfProduction = carImportDto.getYearOfProduction();
                    Car car = this.carRepository.findByBrandAndModelAndYearOfProduction(brand, model, yearOfProduction).
                            orElse(null);
                    if (car != null) {
                        importResult.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                        return;
                    }
                    car = this.modelMapper.map(carImportDto, Car.class);
                    Racer racer = this.racerRepository.findByName(carImportDto.getRacerName()).orElse(null);
                    car.setRacer(racer);
                    this.carRepository.saveAndFlush(car);
                    importResult.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE_CAR,
                            car.getBrand(),
                            car.getModel(),
                            car.getYearOfProduction())).
                            append(System.lineSeparator());
                });
        return importResult.toString().trim();
    }
}
