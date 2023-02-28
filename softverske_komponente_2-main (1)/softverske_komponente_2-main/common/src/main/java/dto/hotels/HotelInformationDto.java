package dto.hotels;

import models.HotelRoom;
import models.Reviews;

import java.util.List;

public class HotelInformationDto {

    private Long id;
    private String hotelName;
    private String city;
    private List<HotelRoom> roomTypes;
    private List<Reviews> reviews;
    private String description;

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

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<HotelRoom> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<HotelRoom> roomTypes) {
        this.roomTypes = roomTypes;
    }

}
