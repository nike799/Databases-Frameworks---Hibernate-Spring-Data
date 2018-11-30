package productsshop.domain.dto.userdto;

import productsshop.domain.dto.productdto.SoldProductExportDto;
import productsshop.domain.dto.productdto.SoldProductExportRootDto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldProductsExportDto {
    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlElement(name = "sold-products")
    private SoldProductExportRootDto soldProductExportRootDto;

    public UserSoldProductsExportDto() {
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

    public SoldProductExportRootDto getSoldProductExportRootDto() {
        return soldProductExportRootDto;
    }

    public void setSoldProductExportRootDto(SoldProductExportRootDto soldProductExportRootDto) {
        this.soldProductExportRootDto = soldProductExportRootDto;
    }
}
