package cardealer.domain.dto.cardto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerTotalSalesExportDto {
    @XmlAttribute(name = "full-name")
    private String fullName;
    @XmlAttribute(name = "bought-cars")
    private Integer boughtCars;
    @XmlAttribute(name = "spent-money")
    private BigDecimal spentMoney;

    public CustomerTotalSalesExportDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullNam) {
        this.fullName = fullNam;
    }

    public Integer getBoughtCars() {
        return boughtCars;
    }

    public void setBoughtCars(Integer boughtCars) {
        this.boughtCars = boughtCars;
    }

    public BigDecimal getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(BigDecimal spentMoney) {
        this.spentMoney = spentMoney;
    }
}
