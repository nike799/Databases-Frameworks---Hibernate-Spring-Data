package mostwanted.domain.dtos.racedtos;

import mostwanted.domain.dtos.raceentrydtos.RaceEntryIdRootDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "race")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceImportDto {
    @XmlElement(name = "laps",required = true, defaultValue = "0")
    private Integer laps;
    @XmlElement(name = "district-name",required = true)
    private String districtName;
    @XmlElement(name = "entries")
    private RaceEntryIdRootDto entries;

    public RaceImportDto() {
    }

    public Integer getLaps() {
        return laps;
    }

    public void setLaps(Integer laps) {
        this.laps = laps;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public RaceEntryIdRootDto getEntries() {
        return entries;
    }

    public void setEntries(RaceEntryIdRootDto entries) {
        this.entries = entries;
    }
}
