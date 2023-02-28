package rest.controllers;

import exceptions.RequestException;
import middleware.Authorization;
import models.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import repositories.HotelRepo;
import rest.services.HotelAdminService;

@SuppressWarnings("all")

@RestController
@RequestMapping("/hotels/admin")
public class AdminController {

    @Autowired
    private HotelRepo hotelRepo;

    @GetMapping(value = "/getall")
    public ResponseEntity<?> getAll(@Nullable @RequestHeader(value = "authorization") String token){
        try{
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            return HotelAdminService.getAll(hotelRepo);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<?> getOne(@Nullable @RequestHeader(value = "authorization") String token, @PathVariable Long id){
        try{
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            return HotelAdminService.getOne(hotelRepo, id);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateOne(@Nullable @RequestHeader(value = "authorization") String token, @PathVariable Long id, @RequestBody Hotel hotel){
        try{
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            return HotelAdminService.updateOne(hotelRepo, id, hotel);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteOne(@Nullable @RequestHeader(value = "authorization") String token, @PathVariable Long id){
        try{
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            return HotelAdminService.deleteOne(hotelRepo, id);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
