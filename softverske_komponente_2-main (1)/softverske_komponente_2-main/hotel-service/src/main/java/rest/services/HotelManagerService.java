package rest.services;

import dto.entities.RoomChangeDto;
import dto.hotels.HotelUpdateDto;
import exceptions.RequestException;
import mappers.HotelMapper;
import models.Hotel;
import models.HotelRoom;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import repositories.HotelRepo;
import rest.validationh.HotelValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class HotelManagerService {

    private static final RequestException managerException = new RequestException("You are not the manager of any hotel, add it to the database", HttpStatus.BAD_REQUEST);

    public static ResponseEntity<?> getMyHotel(Long managerId, HotelRepo hotelRepo) throws RequestException {
       try{
           Hotel hotel = hotelRepo.findByManagerId(managerId).get();
           return new ResponseEntity<>(HotelMapper.fromHotel(hotel), HttpStatus.OK);
       }catch (NoSuchElementException e){
           throw managerException;
       }
    }

    public static ResponseEntity<?> editMyHotel(Long managerId, HotelRepo hotelRepo, HotelUpdateDto dto) throws RequestException{
        try{
            Hotel hotel = hotelRepo.findByManagerId(managerId).get();
            HotelValidation.editMyHotel(dto, hotel, hotelRepo);
            hotelRepo.save(hotel);
            return new ResponseEntity<>("Successfully edited info", HttpStatus.OK);
        }catch (NoSuchElementException e){
            throw managerException;
        }
    }

    public static ResponseEntity<?> addMyHotel(Long managerId, HotelRepo hotelRepo, HotelUpdateDto dto) throws RequestException{
        try{
            Hotel hotel = hotelRepo.findByManagerId(managerId).get();
            throw new RequestException("You are already a manager of hotel: " + hotel.getHotelName(), HttpStatus.BAD_REQUEST);
        }catch (NoSuchElementException e){
            Hotel newHotel = HotelValidation.addMyHotel(managerId, dto, hotelRepo);
            hotelRepo.save(newHotel);
            return new ResponseEntity<>("Successfully added hotel", HttpStatus.OK);
        }
    }

    public static ResponseEntity<?> editRooms(Long managerId, HotelRepo hotelRepo, List<RoomChangeDto> rooms) throws RequestException{
        try {
            Hotel hotel = hotelRepo.findByManagerId(managerId).get();
            HotelValidation.editHotelRooms(rooms);
            List<HotelRoom> newRooms = new ArrayList<>();
            for (RoomChangeDto r : rooms){
                for(int i = r.getLower(); i <= r.getUpper(); i++){
                    newRooms.add(new HotelRoom(r.getRoomType(), r.getPrice(), i));
                }
            }
            hotel.setRoomTypes(newRooms);
            hotelRepo.save(hotel);
            return new ResponseEntity<>("Successfully edited rooms", HttpStatus.OK);
        }catch (NoSuchElementException e){
            throw managerException;
        }catch (NumberFormatException | IndexOutOfBoundsException e){
            throw new RequestException("Invalid layout format for room layout  (x-y)", HttpStatus.BAD_REQUEST);
        }
    }

}
