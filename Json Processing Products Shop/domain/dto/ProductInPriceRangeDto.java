package productsshop.domain.dto;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class ProductInPriceRangeDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private String seller;

    public ProductInPriceRangeDto() {
    }

    public String getName() {
        return name;
    }

    public ProductInPriceRangeDto(String name, BigDecimal price, String seller) {
        this.name = name;
        this.price = price;
        this.seller = seller;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
