package rest.services;

import api.service.ServiceApi;
import dto.entities.ReservationDto;
import enums.UserRanks;
import exceptions.RequestException;
import io.github.resilience4j.retry.Retry;
import mappers.EntityMapper;
import models.Hotel;
import models.Reservation;
import org.joda.time.Days;
import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import repositories.HotelRepo;
import repositories.ReservationRepo;
import rest.validationh.ReservationValidation;
import java.util.List;
import java.util.NoSuchElementException;

@SuppressWarnings("all")

public class ReservationUserService {

    public static ResponseEntity<?> getMyReservations(ReservationRepo reservationRepo, Long userId) throws RequestException {
        try{
            List<Reservation> reservations = reservationRepo.findByUserId(userId).get();
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        }catch (NoSuchElementException n){
            throw new RequestException("You haven't made any reservations", HttpStatus.ACCEPTED);
        }
    }

    public static ResponseEntity<?> makeReservation(Long userId, ReservationDto dto, ReservationRepo reservationRepo,
                                                    HotelRepo hotelRepo, String token, JmsTemplate jmsTemplate, String email, Retry adjustPointsRetry) throws RequestException{
        Hotel hotel = ReservationValidation.checkAvailability(reservationRepo,dto, hotelRepo);
        String hotelName = hotel.getHotelName();
        Reservation res = EntityMapper.fromReservationDto(userId,hotelName, dto);
        try {
            DateTime from = ReservationValidation.formatter.parseDateTime(dto.getFrom()), until = ReservationValidation.formatter.parseDateTime(dto.getUntil());
            int days = Days.daysBetween(from.withTimeAtStartOfDay(), until.withTimeAtStartOfDay()).getDays();
            days = days == 0 ? 1 : days;
            int tries = 0;
            boolean success =
                    Retry.decorateSupplier(adjustPointsRetry, ()->ServiceApi.adjustPoints(token, userId, UserRanks.MAKE_RESERVATION, hotel.getManagerId(), hotelName)).get();
            if(!success)
                throw new RequestException("Service unavailable, please try again later", HttpStatus.INTERNAL_SERVER_ERROR);
            reservationRepo.save(res);
        }catch (IllegalFieldValueException f){
            throw new RequestException("Invalid time format for room reservation", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully made a reservation at: " + hotelName, HttpStatus.OK);
    }

    public static ResponseEntity<?> cancelReservation(Long userId, Long reservationId, ReservationRepo reservationRepo, String token, HotelRepo hotelRepo, Retry adjustPointsRetry) throws RequestException{
        try{
            Reservation res = reservationRepo.findByIdAndUserId(reservationId, userId).get();
            Hotel hotel;
            try{
                  hotel = hotelRepo.findById(res.getHotelId()).get();
            }catch (NoSuchElementException i){
                throw new RequestException("Bad entity", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            int tries = 0;
            boolean success =
                    Retry.decorateSupplier(adjustPointsRetry, ()->ServiceApi.adjustPoints(token, userId, UserRanks.CANCEL_RESERVATION, hotel.getManagerId(), hotel.getHotelName())).get();
            if(!success)
                throw new RequestException("Service unavailable, please try again later", HttpStatus.INTERNAL_SERVER_ERROR);
            reservationRepo.deleteById(reservationId);
            return new ResponseEntity<>("Successfully canceled reservation at: " + res.getHotelName(), HttpStatus.OK);
        }catch (NoSuchElementException req){
            throw new RequestException("No such reservation", HttpStatus.BAD_REQUEST);
        }
    }

}
