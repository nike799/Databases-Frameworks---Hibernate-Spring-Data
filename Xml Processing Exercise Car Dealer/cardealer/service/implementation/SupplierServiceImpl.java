package cardealer.service.implementation;

import cardealer.domain.dto.supplierdto.SupplierExportDto;
import cardealer.domain.dto.supplierdto.SupplierExportRootDto;
import cardealer.domain.dto.supplierdto.SupplierImportRootDto;
import cardealer.domain.model.Supplier;
import cardealer.repository.SupplierRepository;
import cardealer.service.contract.SupplierService;
import cardealer.web.constant.PathFileConstant;
import cardealer.web.parser.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Arrays;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository,
                               ModelMapper modelMapper, XmlParser xmlParser) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedData() throws IOException, JAXBException {
        if (this.supplierRepository.count() > 0) {
            return;
        }
        SupplierImportRootDto supplierImportRootDto =
                xmlParser.parseXml(SupplierImportRootDto.class, PathFileConstant.SUPPLIERS_XML_PATH_FILE);
        Supplier[] suppliers = this.modelMapper.map(supplierImportRootDto.getSuppliers(), Supplier[].class);
        Arrays.stream(suppliers).forEach(supplierRepository::saveAndFlush);
    }

    @Override
    public void getAllByImporterIsFalseAndExportToXml() throws JAXBException {
        Supplier[] suppliers = this.supplierRepository.findAllByImporterIsFalse();
        SupplierExportDto[] supplierExportDtos = this.modelMapper.map(suppliers, SupplierExportDto[].class);
        for (int i = 0; i < supplierExportDtos.length; i++) {
            supplierExportDtos[i].setPartsCount((long) suppliers[i].getParts().size());
        }
        SupplierExportRootDto supplierExportRootDto = new SupplierExportRootDto();
        supplierExportRootDto.setSupplierExportDtos(supplierExportDtos);
        this.xmlParser.exportToXml(supplierExportRootDto,SupplierExportRootDto.class,PathFileConstant.LOCAL_SUPPLIERS_XML_PATH_FILE);
    }
}
