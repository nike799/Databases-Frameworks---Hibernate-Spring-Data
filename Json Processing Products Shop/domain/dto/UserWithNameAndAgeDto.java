package productsshop.domain.dto;


import com.google.gson.annotations.Expose;

import java.util.List;

public class UserWithNameAndAgeDto extends UserDto {
    @Expose
    private List<ProductDto> soldProducts;

    public UserWithNameAndAgeDto() {
    }

    public UserWithNameAndAgeDto(String firstName, String lastName, Integer age, List<ProductDto> soldProducts) {
        super(firstName, lastName, age);
        this.soldProducts = soldProducts;
    }

    public List<ProductDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
