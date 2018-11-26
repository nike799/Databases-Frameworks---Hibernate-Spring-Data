package cardealer.domain.dto.supplierdto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierImportRootDto {
    @XmlElement(name = "supplier")
    private SupplierImporterDto[] suppliers;

    public SupplierImportRootDto() {
    }

    public SupplierImporterDto[] getSuppliers(){
        return suppliers;
    }

    public void setSuppliers(SupplierImporterDto[] suppliers) {
        this.suppliers = suppliers;
    }
}
