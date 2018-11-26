package cardealer.domain.dto.cardto;

import cardealer.domain.dto.partdto.PartWithNameAndPriceExportDto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarWithPartsExportDto {
    @XmlAttribute(name = "make")
    private String make;
    @XmlAttribute(name = "model")
    private String model;
    @XmlAttribute(name = "travelled-distance")
    private Long travelledDistance;
    @XmlElement(name = "part")
    private PartWithNameAndPriceExportDto[] partWithNameAndPriceExportDtos;

    public CarWithPartsExportDto() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public PartWithNameAndPriceExportDto[] getPartWithNameAndPriceExportDtos() {
        return partWithNameAndPriceExportDtos;
    }

    public void setPartWithNameAndPriceExportDtos(PartWithNameAndPriceExportDto[] partWithNameAndPriceExportDtos) {
        this.partWithNameAndPriceExportDtos = partWithNameAndPriceExportDtos;
    }
}
