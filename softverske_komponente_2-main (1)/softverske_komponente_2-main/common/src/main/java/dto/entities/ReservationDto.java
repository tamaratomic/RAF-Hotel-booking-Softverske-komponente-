package dto.entities;

import models.Hotel;
import models.HotelRoom;

import javax.persistence.Id;
import java.util.Date;

public class ReservationDto {

    private Long hotelId;
    private String from;
    private String until;
    private HotelRoom room;

    public ReservationDto(Long hotelId, String from, String until,  HotelRoom room, String hotelName) {
        this.hotelId = hotelId;
        this.from = from;
        this.room = room;
        this.until = until;
    }

    public ReservationDto(){}

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }

    public HotelRoom getRoom() {
        return room;
    }

    public void setRoom(HotelRoom room) {
        this.room = room;
    }
}
