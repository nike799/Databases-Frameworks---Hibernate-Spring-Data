package productsshop.domain.dto.productdto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductExportRootDto {
    @XmlElement(name = "product")
    private ProductExportDto[]productExportDtos;

    public ProductExportRootDto() {
    }

    public ProductExportDto[] getProductExportDtos() {
        return productExportDtos;
    }

    public void setProductExportDtos(ProductExportDto[] productExportDtos) {
        this.productExportDtos = productExportDtos;
    }
}
