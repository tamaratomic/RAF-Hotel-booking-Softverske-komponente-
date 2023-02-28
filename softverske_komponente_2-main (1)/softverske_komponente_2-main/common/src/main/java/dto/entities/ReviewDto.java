package dto.entities;

public class ReviewDto {

    private String comment;
    private Integer stars;


    public ReviewDto(){}

    public ReviewDto(String comment, Integer stars) {
        this.comment = comment;
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

}
