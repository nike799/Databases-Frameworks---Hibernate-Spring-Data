package productsshop.domain.dto;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UserWithSoldItemsDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private List<ProductWithBuyerDto> soldProducts;

    public UserWithSoldItemsDto() {
    }

    public UserWithSoldItemsDto(String firstName, String lastName,
                                List<ProductWithBuyerDto> soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.soldProducts = soldProducts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ProductWithBuyerDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductWithBuyerDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
