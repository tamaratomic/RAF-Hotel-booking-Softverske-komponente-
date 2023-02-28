package models;
import javax.persistence.Embeddable;

@Embeddable
public class HotelRoom {

    private String roomType;
    private Double price;
    private Integer roomNumber;

    public HotelRoom(String roomType, Double price, Integer quantity){
        this.roomType = roomType;
        this.price = price;
        this.roomNumber = quantity;
    }

    public HotelRoom() {

    }

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

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public boolean compareRooms(HotelRoom hotelRoom){
        return this.getRoomType().equals(hotelRoom.getRoomType())
                && this.getPrice().equals(hotelRoom.getPrice())
                && this.getRoomNumber().equals(hotelRoom.getRoomNumber());
    }
}
