package rest.services;

import exceptions.RequestException;
import models.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import repositories.ReservationRepo;

import java.util.List;
import java.util.NoSuchElementException;

public class ReservationAdminService {

    public static ResponseEntity<?> getAll(ReservationRepo reservationRepo){
        List<Reservation> reservations = reservationRepo.findAll();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    public static ResponseEntity<?> getOne(ReservationRepo reservationRepo, Long reservationId) throws RequestException{
        try{
            Reservation res = reservationRepo.findById(reservationId).get();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }catch (NoSuchElementException e){
            throw new RequestException("Requested reservation is not in the database", HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<?> deleteOne(ReservationRepo reservationRepo, Long reservationId) throws RequestException{
        try{
            Reservation res = reservationRepo.findById(reservationId).get();
            reservationRepo.deleteById(reservationId);
            return new ResponseEntity<>("Successfully deleted reservation", HttpStatus.OK);
        }catch (NoSuchElementException e){
            throw new RequestException("Requested reservation is not in the database", HttpStatus.BAD_REQUEST);
        }
    }

}
