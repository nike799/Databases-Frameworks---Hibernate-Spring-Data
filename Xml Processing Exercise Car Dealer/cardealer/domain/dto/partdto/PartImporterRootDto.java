package cardealer.domain.dto.partdto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartImporterRootDto {
    @XmlElement(name = "part")
    private PartImporterDto[] parts;

    public PartImporterRootDto() {
    }

    public PartImporterDto[] getParts() {
        return parts;
    }

    public void setParts(PartImporterDto[] parts) {
        this.parts = parts;
    }
}
