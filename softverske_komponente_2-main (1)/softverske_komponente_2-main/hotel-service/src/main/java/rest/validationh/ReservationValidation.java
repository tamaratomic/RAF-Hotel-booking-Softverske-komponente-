package rest.validationh;

import dto.entities.ReservationDto;
import exceptions.RequestException;
import models.Hotel;
import models.HotelRoom;
import models.Reservation;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import repositories.HotelRepo;
import repositories.ReservationRepo;

import java.util.List;
import java.util.NoSuchElementException;

public class ReservationValidation {

    public static final DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");

    public static Hotel checkAvailability(ReservationRepo reservationRepo, ReservationDto reservationDto, HotelRepo hotelRepo) throws RequestException {
        Hotel hotel;
        try{
            hotel = hotelRepo.findById(reservationDto.getHotelId()).get();
            boolean found = false;
            for(HotelRoom r : hotel.getRoomTypes())
                if(r.compareRooms(reservationDto.getRoom())){
                    found = true;
                    break;
                }
            if (!found)
                throw new RequestException("Hotel " + hotel.getHotelName() + " doesn't have that room type", HttpStatus.BAD_REQUEST);
            DateTime from = formatter.parseDateTime(reservationDto.getFrom()), until = formatter.parseDateTime(reservationDto.getUntil());
            if(from.isAfter(until))
                throw new RequestException("Reservation begin date can't be less than end date", HttpStatus.BAD_REQUEST);
            if(from.isBefore(DateTime.now()))
                throw new RequestException("Reservation cannot be made in the past :)", HttpStatus.BAD_REQUEST);
        }catch (NoSuchElementException e){
           throw new RequestException("Requested hotel, for reservation is not in the database", HttpStatus.BAD_REQUEST);
        }
        List<Reservation> reservations = reservationRepo.findByHotelIdAndRoom(reservationDto.getHotelId(), reservationDto.getRoom());
        DateTime resFrom, resUntil, userFrom, userUntil;
        for (Reservation r : reservations) {
            resFrom = formatter.parseDateTime(r.getFrom());
            resUntil = formatter.parseDateTime(r.getUntil());
            userFrom = formatter.parseDateTime(reservationDto.getFrom());
            userUntil = formatter.parseDateTime(reservationDto.getUntil());
            if (new Interval(resFrom, resUntil).overlaps(new Interval(userFrom, userUntil)) || r.getUntil().equals(reservationDto.getFrom()))
                throw new RequestException("Room not available", HttpStatus.BAD_REQUEST);
        }
        return hotel;
    }
}
