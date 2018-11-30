package productsshop.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productsshop.domain.dto.productdto.SoldProductExportDto;
import productsshop.domain.dto.productdto.SoldProductExportRootDto;
import productsshop.domain.dto.userdto.UserImportRootDto;
import productsshop.domain.dto.userdto.UserSoldProductsExportDto;
import productsshop.domain.dto.userdto.UserSoldProductsExportRootDto;
import productsshop.domain.model.User;
import productsshop.repository.UserRepository;
import productsshop.service.contract.UserService;
import productsshop.util.contract.FileUtil;
import productsshop.util.contract.ValidatorUtil;
import productsshop.web.parser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {
    private static final String USERS_XML_PATH_FILE =
            "C:\\Users\\Nike\\Desktop\\Softuni Projects\\XmlProcessingProductsShop\\src\\main\\resources\\files\\import\\users.xml";
    private final static String USERS_SOLD_PRODUCTS_XML_PATH_FILE=
            "C:\\Users\\Nike\\Desktop\\Softuni Projects\\XmlProcessingProductsShop\\src\\main\\resources\\files\\export\\users-sold-products.xml";
    private final UserRepository userRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, FileUtil fileUtil,
                           XmlParser xmlParser, ModelMapper mapper, ValidatorUtil validatorUtil) {
        this.userRepository = userRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;

        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public void seedUsers() throws IOException, JAXBException {
        if (this.userRepository.count() > 0) {
            return;
        }
        UserImportRootDto userImportRootDtos = this.xmlParser.parseXml(UserImportRootDto.class, USERS_XML_PATH_FILE);
        User[] users = this.mapper.map(userImportRootDtos.getUserImportDtos(), User[].class);
        for (User user : users) {
            this.userRepository.saveAndFlush(user);
        }
    }

    @Override
    public void getAllUsersWhoHaveAtLeastOneProductSoldWithBuyer() throws JAXBException {
        User[] users = this.userRepository.findAllUsersWhoHaveAtLeastOneProductsoldWitnBuyer();
        UserSoldProductsExportRootDto userSoldProductsExportRootDto = new UserSoldProductsExportRootDto();
        UserSoldProductsExportDto[] userSoldProductsExportDto = this.mapper.map(users, UserSoldProductsExportDto[].class);
        int index = 0;
        for (User user : users) {
            SoldProductExportRootDto soldProductExportRootDto = new SoldProductExportRootDto();
            SoldProductExportDto[] soldProductExportDtos = this.mapper.map(user.getSoldProducts(), SoldProductExportDto[].class);
            soldProductExportRootDto.setSoldProductExportDtos(soldProductExportDtos);
            userSoldProductsExportDto[index].setSoldProductExportRootDto(soldProductExportRootDto);
            index++;
        }
        userSoldProductsExportRootDto.setUserSoldProductsExportDtos(userSoldProductsExportDto);
        this.xmlParser.exportToXml(userSoldProductsExportRootDto,UserSoldProductsExportRootDto.class,USERS_SOLD_PRODUCTS_XML_PATH_FILE);
    }
}
