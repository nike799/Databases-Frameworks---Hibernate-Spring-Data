package cardealer.domain.dto.saledto;
import cardealer.domain.dto.cardto.CarWithoutIdExportDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
@XmlRootElement(name = "sale")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleWithAppliedDiscountExportDto {
    @XmlElement(name = "car")
    private CarWithoutIdExportDto carExportDto;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "discount")
    private Double discount;
    @XmlElement(name = "price")
    private BigDecimal price;
    @XmlElement(name = "price-with-discount")
    private BigDecimal priceWithDiscount;

    public SaleWithAppliedDiscountExportDto() {
    }

    public CarWithoutIdExportDto getCarExportDto() {
        return carExportDto;
    }

    public void setCarExportDto(CarWithoutIdExportDto carExportDto) {
        this.carExportDto = carExportDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithoutDiscount) {
        this.priceWithDiscount = priceWithoutDiscount;
    }
}
