package enums;

import models.HotelRoom;

import java.util.ArrayList;
import java.util.List;

public class RoomTypes {

    public static List<HotelRoom> defaultRooms = new ArrayList<>();

    public static final String SINGLE = "Single";
    public static final String DOUBLE = "Double";
    public static final String APARTMENT = "Apartment";
    public static final String SUITE = "Suite";

    public static final Double SINGLE_PRICE = 1700.0;
    public static final Double DOUBLE_PRICE = 3200.0;
    public static final Double APARTMENT_PRICE = 6530.0;
    public static final Double SUITE_PRICE = 11000.0;

    public static final Integer SINGLE_QUANTITY = 5;
    public static final Integer DOUBLE_QUANTITY = 3;
    public static final Integer APARTMENT_QUANTITY = 2;
    public static final Integer SUITE_QUANTITY = 1;

    static {
        for (int i = 1; i <= 11; i++){
            if(i <= 5){
                defaultRooms.add(new HotelRoom(SINGLE, SINGLE_PRICE, i));
            }else if (i <= 8){
                defaultRooms.add(new HotelRoom(DOUBLE, DOUBLE_PRICE, i));
            }else if (i <= 10){
                defaultRooms.add(new HotelRoom(APARTMENT, APARTMENT_PRICE, i));
            }else
                defaultRooms.add(new HotelRoom(SUITE, SUITE_PRICE, i));
        }
    }
}
