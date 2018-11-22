package productsshop.domain.dto;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    public ProductDto() {
    }

    public ProductDto(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    @NotNull(message = "Product name cannot be null.")
    @Length(min = 3,message = "Product name must have at least 3 characters.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Price cannot be null.")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
