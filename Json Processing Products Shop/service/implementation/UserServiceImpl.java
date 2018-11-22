package productsshop.service.implementation;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productsshop.domain.dto.*;
import productsshop.domain.model.User;
import productsshop.repository.UserRepository;
import productsshop.service.contract.UserService;
import productsshop.web.util.io.contract.FileUtil;
import productsshop.web.util.io.contract.ValidatorUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String USER_JSON_PATH_FILE =
            "C:\\Users\\Nike\\Downloads\\JsonProcessingProductsShop\\src\\main\\resources\\files\\users.json";
    private static final String FILE_PATH_USERS_AND_PRODUCTS =
            "C:\\Users\\Nike\\Downloads\\JsonProcessingProductsShop\\src\\main\\resources\\files\\users-and-products.json";
    private static final String FILE_PATH_users_SOLD_PRODUCTS_JSON =
            "C:\\Users\\Nike\\Downloads\\JsonProcessingProductsShop\\src\\main\\resources\\files\\users-sold-products.json";
    private final UserRepository userRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, FileUtil fileUtil,
                           Gson gson, ModelMapper mapper, ValidatorUtil validatorUtil) {
        this.userRepository = userRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public void seedUsers() throws IOException {
        if (this.userRepository.count() > 0) {
            return;
        }
        String userFileContent = this.fileUtil.getFileContent(USER_JSON_PATH_FILE);
        UserDto[] userDtos = this.gson.fromJson(userFileContent, UserDto[].class);
        for (UserDto dto : userDtos) {
            if (!this.validatorUtil.isValid(dto)) {
                this.validatorUtil.violations(dto).
                        forEach(violation -> System.out.println(violation.getMessage()));
                continue;
            }
            User entity = this.mapper.map(dto, User.class);
            this.userRepository.saveAndFlush(entity);
        }
    }

    @Override
    public void getAllUsersByAtLeastOneProductSoldWithTheirBuyer() throws IOException {
        File file = new File(FILE_PATH_users_SOLD_PRODUCTS_JSON);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("");
        List<UserWithSoldItemsDto> userWithSoldItemsDtos = new LinkedList<>();
        List<User> entities = this.userRepository.findAll().stream().
                filter(user -> user.getSoldProducts().size() > 0 && user.getFirstName() != null).
                sorted((u1, u2) -> {
                    if (u1.getFirstName().compareTo(u2.getFirstName()) != 0) {
                        return u1.getFirstName().compareTo(u2.getFirstName());
                    } else {
                        return u1.getLastName().compareTo(u2.getLastName());
                    }
                }).collect(Collectors.toList());

        entities.forEach(entity -> {
            UserWithSoldItemsDto userWithSoldItemsDto = this.mapper.map(entity, UserWithSoldItemsDto.class);
            List<ProductWithBuyerDto> productWithBuyerDtos = new LinkedList<>();
            entity.getSoldProducts().stream().filter(product -> product.getBuyer() != null).forEach(product -> {
                ProductWithBuyerDto productWithBuyerDto = this.mapper.map(product, ProductWithBuyerDto.class);
                productWithBuyerDto.setBuyerFirstName(product.getBuyer().getFirstName());
                productWithBuyerDto.setBuyerLastName(product.getBuyer().getLastName());
                productWithBuyerDtos.add(productWithBuyerDto);
            });
            userWithSoldItemsDto.setSoldProducts(productWithBuyerDtos);
            userWithSoldItemsDtos.add(userWithSoldItemsDto);
        });
        String userWithSoldItemsDtosToJson = this.gson.toJson(userWithSoldItemsDtos);
        fileWriter.write(userWithSoldItemsDtosToJson);
        fileWriter.close();
    }

    @Override
    public void getAllUsersByAtLeastOneProductSold() throws IOException {
        File file = new File(FILE_PATH_USERS_AND_PRODUCTS);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("");
        List<User> entities = this.userRepository.findAll().stream().filter(user -> user.getSoldProducts().size() > 0).collect(Collectors.toList());
        List<UserWithNameAndAgeDto> userWithNameAndAgeDtos = entities.stream().
                sorted((u1, u2) -> Integer.compare(u2.getSoldProducts().size(), u1.getSoldProducts().size())).
                map(entity -> this.mapper.map(entity, UserWithNameAndAgeDto.class)).collect(Collectors.toList());
        UsersAndProductsDto usersAndProductsDto = new UsersAndProductsDto();
        usersAndProductsDto.setUsers(userWithNameAndAgeDtos);
        usersAndProductsDto.setUsersCount();
        String userWithNameAndAge = this.gson.toJson(usersAndProductsDto);
        fileWriter.write(userWithNameAndAge);

        System.out.println();
    }

}
