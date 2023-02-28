package dto.hotels;

import models.HotelRoom;

import java.util.List;

public class HotelUpdateDto {

    private String hotelName;
    private String city;
    private List<HotelRoom> roomTypes;
    private String description;

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

    public List<HotelRoom> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<HotelRoom> roomTypes) {
        this.roomTypes = roomTypes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
