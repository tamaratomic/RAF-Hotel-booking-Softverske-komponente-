package models;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
public class Reviews {
    private String comment;
    private Integer stars;
    private Long userId;
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean equals_review(Reviews review) {
        return (Objects.equals(review.getUserId(), this.getUserId()));
    }
}
