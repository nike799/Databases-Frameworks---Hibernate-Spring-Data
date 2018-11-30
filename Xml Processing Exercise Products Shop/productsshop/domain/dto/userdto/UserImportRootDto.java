package productsshop.domain.dto.userdto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserImportRootDto {
    @XmlElement(name = "user")
    private UserImportDto[] userImportDtos;

    public UserImportDto[] getUserImportDtos() {
        return userImportDtos;
    }

    public void setUserImportDtos(UserImportDto[] userImportDtos) {
        this.userImportDtos = userImportDtos;
    }
}
