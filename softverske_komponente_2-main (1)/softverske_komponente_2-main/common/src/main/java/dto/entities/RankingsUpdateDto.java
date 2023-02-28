package dto.entities;

public class RankingsUpdateDto {

    private Integer silverPoints;
    private Integer goldPoints;

    public void setSilverPoints(Integer silverPoints) {
        this.silverPoints = silverPoints;
    }

    public void setGoldPoints(Integer goldPoints) {
        this.goldPoints = goldPoints;
    }

    public Integer getSilverPoints() {
        return silverPoints;
    }

    public Integer getGoldPoints() {
        return goldPoints;
    }

    @Override
    public String toString() {
        return "silverPoints: " + silverPoints +
                ", goldPoints: " + goldPoints;
    }
}
