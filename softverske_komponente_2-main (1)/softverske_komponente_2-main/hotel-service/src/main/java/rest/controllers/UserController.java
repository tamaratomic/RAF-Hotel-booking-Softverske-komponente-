package rest.controllers;

import api.ApiCommon;
import dto.entities.ReviewDto;
import dto.entities.TokenDto;
import exceptions.RequestException;
import middleware.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import repositories.HotelRepo;
import rest.services.HotelAdminService;
import rest.services.HotelUserService;

@SuppressWarnings("all")

@RestController
@RequestMapping("/hotels/users")
public class UserController {

    @Autowired
    private HotelRepo hotelRepo;

    @GetMapping(value = "/getall")
    public ResponseEntity<?> getAll(@Nullable @RequestHeader(value = "authorization") String token){
        try{
            Authorization.checkAuthorization(token);
            return HotelAdminService.getAll(hotelRepo);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/review/{id}")
    public ResponseEntity<?> reviewHotel(@RequestHeader(value = "authorization", required = false) String token, @PathVariable Long id
    , @RequestBody ReviewDto reviewDto){
        try{
            Authorization.checkAuthorization(token);
            TokenDto userInfo = ApiCommon.getFromToken(token);
            return HotelUserService.reviewHotel(hotelRepo, userInfo, id, reviewDto);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/review/edit/{id}")
    public ResponseEntity<?> editReview(@RequestHeader(value = "authorization", required = false) String token, @PathVariable Long id, @RequestBody ReviewDto reviewDto){
        try{
            Authorization.checkAuthorization(token);
            TokenDto userInfo = ApiCommon.getFromToken(token);
            return HotelUserService.editReview(hotelRepo, id, userInfo.getId(), reviewDto);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/review/delete/{id}")
    public ResponseEntity<?> deleteReview(@RequestHeader(value = "authorization", required = false) String token, @PathVariable Long id){
        try{
            Authorization.checkAuthorization(token);
            Long userId = ApiCommon.getFromToken(token).getId();
            return HotelUserService.deleteReview(hotelRepo, id, userId);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
