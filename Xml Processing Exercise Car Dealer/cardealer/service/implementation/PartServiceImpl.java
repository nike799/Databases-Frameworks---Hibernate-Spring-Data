package cardealer.service.implementation;

import cardealer.domain.dto.partdto.PartImporterRootDto;
import cardealer.domain.model.Part;
import cardealer.domain.model.Supplier;
import cardealer.repository.PartRepository;
import cardealer.repository.SupplierRepository;
import cardealer.service.contract.PartService;
import cardealer.web.constant.PathFileConstant;
import cardealer.web.parser.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public PartServiceImpl(PartRepository repository, SupplierRepository supplierRepository,
                           ModelMapper modelMapper, XmlParser xmlParser) {
        this.partRepository = repository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedData() throws IOException, JAXBException {
        if (this.partRepository.count() > 0) {
            return;
        }
        PartImporterRootDto partImporterRootDto =
                xmlParser.parseXml(PartImporterRootDto.class, PathFileConstant.PARTS_XML_PATH_FILE);
        System.out.println();
        Part[] parts = this.modelMapper.map(partImporterRootDto.getParts(), Part[].class);
        Arrays.stream(parts).forEach(part -> {
            part.setSupplier(getRandomSupplier());
            this.partRepository.saveAndFlush(part);
        });
        System.out.println();
    }

    private Supplier getRandomSupplier() {
        Random random = new Random();
        int id = random.nextInt((int) this.supplierRepository.count());
        if (id == 0) {
            id++;
        }
        return this.supplierRepository.getOne((long) id);

    }
}
