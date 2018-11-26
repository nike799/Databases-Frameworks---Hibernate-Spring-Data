package cardealer.domain.dto.saledto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleWithAppliedDiscountExportRootDto {
    @XmlElement(name = "sale")
    private List<SaleWithAppliedDiscountExportDto> saleWithAppliedDiscountExportDtos;

    public SaleWithAppliedDiscountExportRootDto() {
    }

    public List<SaleWithAppliedDiscountExportDto> getSaleWithAppliedDiscountExportDtos() {
        return saleWithAppliedDiscountExportDtos;
    }

    public void setSaleWithAppliedDiscountExportDtos(List<SaleWithAppliedDiscountExportDto> saleWithAppliedDiscountExportDtos) {
        this.saleWithAppliedDiscountExportDtos = saleWithAppliedDiscountExportDtos;
    }
}
