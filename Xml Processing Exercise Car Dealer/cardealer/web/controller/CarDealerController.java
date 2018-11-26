package cardealer.web.controller;

import cardealer.service.contract.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Controller
public class CarDealerController implements CommandLineRunner {
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private BufferedReader reader;

    public CarDealerController(SupplierService supplierService,
                               PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        this.supplierService.seedData();
        this.partService.seedData();
        this.carService.seedData();
        this.customerService.seedData();
        this.saleService.seedData();

        while (true) {
            System.out.println("Enter the number of query:");
            int queryNum = Integer.parseInt(this.reader.readLine());
            switch (queryNum) {
                case 1:
                    this.customerService.getAllCustomersOrderedByBirthDay();
                    break;
                case 2:
                    this.carService.getAllToyotaCarsAndExportToXml();
                    break;
                case 3:
                    this.supplierService.getAllByImporterIsFalseAndExportToXml();
                    break;
                case 4:
                    carService.getAllCarsWithTheirPartsAndExportedToXml();
                    break;
                case 5:
                    customerService.getAllCustomersWithTotalSales();
                    break;
                case 6:
                    saleService.getAllSalesWithAppliedDiscount();
                    break;
            }
        }
    }
}
