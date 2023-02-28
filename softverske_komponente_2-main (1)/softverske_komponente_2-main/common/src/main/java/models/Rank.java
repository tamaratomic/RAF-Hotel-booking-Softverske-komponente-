package models;


import javax.persistence.Embeddable;

@Embeddable
public class Rank {

    private String rank;
    private Double reduction;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Double getReduction() {
        return reduction;
    }

    public void setReduction(Double reduction) {
        this.reduction = reduction;
    }

}
