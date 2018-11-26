package cardealer.domain.dto.cardto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarWithPartsExportRootDto {
    @XmlElement(name = "car")
    private CarWithPartsExportDto[] carWithPartsExportDtos;

    public CarWithPartsExportRootDto() {
    }

    public CarWithPartsExportDto[] getCarWithPartsExportDtos() {
        return carWithPartsExportDtos;
    }

    public void setCarWithPartsExportDtos(CarWithPartsExportDto[] carWithPartsExportDtos) {
        this.carWithPartsExportDtos = carWithPartsExportDtos;
    }
}
