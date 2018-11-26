package cardealer.domain.dto.cardto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarImporterRootDto {
    @XmlElement(name = "car")
    private CarImporterDto[] cars;

    public CarImporterRootDto() {
    }

    public CarImporterDto[] getCars() {
        return cars;
    }

    public void setCars(CarImporterDto[] cars) {
        this.cars = cars;
    }
}
