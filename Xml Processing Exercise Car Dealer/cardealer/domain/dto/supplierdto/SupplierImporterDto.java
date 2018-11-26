package cardealer.domain.dto.supplierdto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "supplier")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierImporterDto {
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "is-importer")
    private Boolean isImporter;

    public SupplierImporterDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsimporter() {
        return isImporter;
    }

    public void setIsimporter(Boolean isimporter) {
        this.isImporter = isimporter;
    }
}
