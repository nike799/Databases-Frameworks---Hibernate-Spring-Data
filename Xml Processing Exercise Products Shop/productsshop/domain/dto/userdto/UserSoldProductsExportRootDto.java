package productsshop.domain.dto.userdto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldProductsExportRootDto {
    @XmlElement(name = "user")
    private UserSoldProductsExportDto[] userSoldProductsExportDtos;

    public UserSoldProductsExportRootDto() {
    }

    public UserSoldProductsExportDto[] getUserSoldProductsExportDtos() {
        return userSoldProductsExportDtos;
    }

    public void setUserSoldProductsExportDtos(UserSoldProductsExportDto[] userSoldProductsExportDtos) {
        this.userSoldProductsExportDtos = userSoldProductsExportDtos;
    }
}
