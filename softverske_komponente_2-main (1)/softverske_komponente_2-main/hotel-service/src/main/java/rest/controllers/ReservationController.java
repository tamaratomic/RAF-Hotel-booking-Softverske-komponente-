package rest.controllers;


import api.ApiCommon;
import dto.entities.ReservationDto;
import dto.entities.TokenDto;
import exceptions.RequestException;
import io.github.resilience4j.retry.Retry;
import middleware.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import repositories.HotelRepo;
import repositories.ReservationRepo;
import rest.services.ReservationUserService;
import rest.services.ReservationAdminService;

@SuppressWarnings("all")

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepo reservationRepo;
    @Autowired
    private HotelRepo hotelRepo;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Retry adjustPointsRetry;

    @GetMapping(value = "/users/get")
    public ResponseEntity<?> getMyReservations(@RequestHeader(value = "authorization", required = false) String token){
        try{
            Authorization.checkAuthorization(token);
            Long userId = ApiCommon.getFromToken(token).getId();
            return ReservationUserService.getMyReservations(reservationRepo, userId);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
             return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/users/make")
    public ResponseEntity<?> makeReservation(@RequestHeader(value = "authorization", required = false) String token, @RequestBody ReservationDto dto){
        try{
            Authorization.checkAuthorization(token);
            TokenDto info = ApiCommon.getFromToken(token);
            return ReservationUserService.makeReservation(info.getId(), dto, reservationRepo, hotelRepo, token, jmsTemplate, info.getEmail(), adjustPointsRetry);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/users/cancel/{id}")
    public ResponseEntity<?> cancelReservation(@RequestHeader(value = "authorization", required = false) String token, @PathVariable Long id){
        try{
            Authorization.checkAuthorization(token);
            Long userId = ApiCommon.getFromToken(token).getId();
            return ReservationUserService.cancelReservation(userId, id, reservationRepo, token, hotelRepo, adjustPointsRetry);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    ///ADMIN

    @GetMapping(value = "/get")
    public ResponseEntity<?> getAll(@RequestHeader(value = "authorization", required = false) String token, @RequestBody(required = false) Integer l){
        try{
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            return ReservationAdminService.getAll(reservationRepo);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<?> getOne(@RequestHeader(value = "authorization", required = false) String token, @PathVariable Long id){
        try{
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            return ReservationAdminService.getOne(reservationRepo, id);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteOne(@RequestHeader(value = "authorization", required = false) String token, @PathVariable Long id){
        try{
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            return ReservationAdminService.deleteOne(reservationRepo, id);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    ///Service
    @GetMapping(value = "/service/get")
    public ResponseEntity<?> getAllForService(@RequestHeader(value = "key", required = false) String key){
        try{
            if(!key.equals("37fe66b4-e44b-48e7-932b-4196483786c0"))
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            return ReservationAdminService.getAll(reservationRepo);
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
