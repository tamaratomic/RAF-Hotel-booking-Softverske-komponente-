package mappers;

import dto.hotels.HotelInformationDto;
import dto.hotels.HotelUpdateDto;
import models.Hotel;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HotelMapper {

    public static Hotel fromScratch(String hotelName, String city, Long managerId, @Nullable String description){
        Hotel newHotel = new Hotel();
        newHotel.setHotelName(hotelName);
        newHotel.setCity(city);
        newHotel.setManagerId(managerId);
        newHotel.setDescription(description);
        return newHotel;
    }

    public static HotelInformationDto fromHotel(Hotel hotel){
        HotelInformationDto dto = new HotelInformationDto();
        dto.setId(hotel.getId());
        dto.setRoomTypes(hotel.getRoomTypes());
        dto.setReviews(hotel.getReviews());
        dto.setHotelName(hotel.getHotelName());
        dto.setCity(hotel.getCity());
        dto.setDescription(hotel.getDescription());
        return dto;
    }

    public static List<HotelInformationDto> fromHotel(List<Hotel> hotels){
        List<HotelInformationDto> hotelsInfo = new ArrayList<>();
        for(Hotel h:hotels)
            hotelsInfo.add(fromHotel(h));
        return hotelsInfo;
    }

    public static Hotel fromHotelUpdate(HotelUpdateDto dto, Long managerId){
        Hotel hotel = new Hotel();
        hotel.setHotelName(dto.getHotelName());
        hotel.setCity(dto.getCity());
        hotel.setDescription(dto.getDescription());
        hotel.setRoomTypes(dto.getRoomTypes());
        hotel.setManagerId(managerId);
        return hotel;
    }

}
