package models;

import enums.RoomTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")

@Entity(name = "Hotel")
@EntityListeners(AuditingEntityListener.class)
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "hotel_name")
    private String hotelName;
    @Column(name = "city")
    private String city;
    @Column(name = "managerId")
    private Long managerId;
    @ElementCollection
    @Column(name = "room_types")
    private List<HotelRoom> roomTypes = RoomTypes.defaultRooms;
    @ElementCollection
    @Column(name = "reviews")
    private List<Reviews> reviews = new ArrayList<>();
    @Column(name = "description", length = 600)
    private String description;
    @Column(name = "rating")
    private Double rating = 0.0;
    @CreatedDate
    public Instant createdAt = Instant.now();
    @LastModifiedDate
    public Instant lastModified;


    public void addReview(Reviews rev){
        this.reviews.add(rev);
    }

    public void editReview(Reviews rev, int index){
        this.reviews.remove(index);
        this.reviews.add(index, rev);
    }

    public void deleteReview(Reviews rev){
        this.reviews.remove(rev);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public List<HotelRoom> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<HotelRoom> room_Types) {
        this.roomTypes = room_Types;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void adjustRating(){
        int totalStars = 0, totalReviews = reviews.size();
        if(totalReviews == 0) {
            this.setRating(0.0);
            return;
        }
        for(Reviews rev: reviews)
            totalStars += rev.getStars();
        this.setRating((double)totalStars/(double)totalReviews);
    }

}
