package productsshop.domain.dto.productdto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductExportRootDto {
    @XmlElement(name = "product")
    private SoldProductExportDto[] soldProductExportDtos;

    public SoldProductExportRootDto() {
    }

    public SoldProductExportDto[] getSoldProductExportDtos() {
        return soldProductExportDtos;
    }

    public void setSoldProductExportDtos(SoldProductExportDto[] soldProductExportDtos) {
        this.soldProductExportDtos = soldProductExportDtos;
    }
}
