package mostwanted.domain.dtos.towndtos;

public class TownExportDto {
    private String townName;
    private String countRacers;

    public TownExportDto() {
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getCountRacers() {
        return countRacers;
    }

    public void setCountRacers(String countRacers) {
        this.countRacers = countRacers;
    }

    @Override
    public String toString() {
        return String.format("" +
                        "Name: %s\n" +
                        "Racers: %s\n",
                getTownName(),
                getCountRacers());
    }
}
