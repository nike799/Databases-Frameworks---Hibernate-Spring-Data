package mostwanted.domain.dtos.raceentrydtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntryIdRootDto {
    @XmlElement(name = "entry")
    private RaceEntryIdDto[] raceEntryIdDtos;

    public RaceEntryIdRootDto() {
    }

    public RaceEntryIdDto[] getRaceEntryIdDtos() {
        return raceEntryIdDtos;
    }

    public void setRaceEntryIdDtos(RaceEntryIdDto[] raceEntryIdDtos) {
        this.raceEntryIdDtos = raceEntryIdDtos;
    }
}
