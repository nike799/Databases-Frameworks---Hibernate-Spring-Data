package cardealer.domain.dto.cardto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarExportRootDto {
    @XmlElement(name = "car")
    private CarExportDto[] carExportDtos;

    public CarExportRootDto() {
    }

    public CarExportDto[] getCarExportDtos() {
        return carExportDtos;
    }

    public void setCarExportDtos(CarExportDto[] carExportDtos) {
        this.carExportDtos = carExportDtos;
    }
}
