package rest.services;

import exceptions.RequestException;
import models.Hotel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import repositories.HotelRepo;
import rest.validationh.HotelValidation;

import java.util.List;
import java.util.NoSuchElementException;

public class HotelAdminService {

    public static ResponseEntity<?> getAll(HotelRepo hotelRepo) throws RequestException{
        List<Hotel> hotels = hotelRepo.findAll();
        if(hotels.isEmpty())
            throw new RequestException("No hotels are in the database", HttpStatus.ACCEPTED);
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    public static ResponseEntity<?> getOne(HotelRepo hotelRepo, Long id)throws RequestException{
        try {
            Hotel hotel = hotelRepo.findById(id).get();
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        }catch (NoSuchElementException e){
            throw new RequestException("Requested hotel is not in the database", HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<?> deleteOne(HotelRepo hotelRepo, Long id) throws RequestException {
        try {
            Hotel hotel = hotelRepo.findById(id).get();
            hotelRepo.deleteById(id);
            return new ResponseEntity<>("Successfully deleted hotel: " + hotel.getHotelName(), HttpStatus.OK);
        }catch (NoSuchElementException e){
            throw new RequestException("Requested hotel is not in the database", HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<?> updateOne(HotelRepo hotelRepo, Long id, Hotel hotelInfo) throws RequestException{
        try{
            HotelValidation.updateHotelAdmin(hotelInfo, hotelRepo);
            Hotel hotel = hotelRepo.findById(id).get();
            String hotelName = hotel.getHotelName();
            Long hotelId = hotel.getId();
            Long managerId = hotel.getManagerId();
            hotel = hotelInfo;
            hotel.setId(hotelId);
            hotel.setManagerId(managerId);
            hotelRepo.save(hotel);
            return new ResponseEntity<>("Successfully updated hotel: " + hotelName, HttpStatus.OK);
        }catch (NoSuchElementException e){
            throw new RequestException("Requested hotel is not in the database", HttpStatus.BAD_REQUEST);
        }
    }

}
