package rest.controllers;

import dto.entities.RoomChangeDto;
import dto.hotels.HotelUpdateDto;
import enums.RequestServer;
import exceptions.RequestException;
import middleware.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import repositories.HotelRepo;
import rest.services.HotelManagerService;

import java.util.List;

@SuppressWarnings("all")

@RestController
@RequestMapping(value = "/hotels/manager")
public class ManagerController {

    @Autowired
    private HotelRepo hotelRepo;

    @GetMapping(value = "/myhotel")
    public ResponseEntity<?> getMyHotel(@Nullable @RequestHeader(value = "authorization") String token){
        try{
            Long managerId = Authorization.checkManager(Authorization.checkAuthorization(token));
            return HotelManagerService.getMyHotel(managerId, hotelRepo);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/myhotel/edit")
    public ResponseEntity<?> editMyHotel(@Nullable @RequestHeader(value = "authorization") String token, @RequestBody HotelUpdateDto updateInfo){
        try {
            Long managerId = Authorization.checkManager(Authorization.checkAuthorization(token));
            return HotelManagerService.editMyHotel(managerId, hotelRepo, updateInfo);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/myhotel/add")
    public ResponseEntity<?> addMyHotel(@Nullable @RequestHeader(value = "authorization") String token, @RequestBody HotelUpdateDto updateInfo){
        try {
            Long managerId = Authorization.checkManager(Authorization.checkAuthorization(token));
            return HotelManagerService.addMyHotel(managerId, hotelRepo, updateInfo);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/myhotel/edit/rooms")
    public ResponseEntity<?> addRoomType(@RequestHeader(value = "authorization") String token, @RequestBody List<RoomChangeDto> roomLayout){
        try {
            Long managerId = Authorization.checkManager(Authorization.checkAuthorization(token));
            return HotelManagerService.editRooms(managerId, hotelRepo, roomLayout);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
