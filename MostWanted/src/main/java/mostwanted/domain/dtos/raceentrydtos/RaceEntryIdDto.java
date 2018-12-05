package mostwanted.domain.dtos.raceentrydtos;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "entry")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntryIdDto {

    @XmlAttribute(name = "id")
    private Integer id;

    public RaceEntryIdDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
