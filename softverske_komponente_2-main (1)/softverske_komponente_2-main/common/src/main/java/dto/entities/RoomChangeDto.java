package dto.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import models.HotelRoom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomChangeDto {


    private String roomType;
    private Double price;
    private Integer lower;
    private Integer upper;

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getLower() {
        return lower;
    }

    public void setLower(Integer lower) {
        this.lower = lower;
    }

    public Integer getUpper() {
        return upper;
    }

    public void setUpper(Integer upper) {
        this.upper = upper;
    }
}
