package rest.controllers;


import dto.entities.RankingsUpdateDto;
import dto.user.UserInformationDto;
import exceptions.RequestException;
import middleware.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.UserRepo;
import rest.services.AdminService;

@SuppressWarnings("all")

@RestController
@RequestMapping("/users")
public class AdminController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping(value = "/get")
    public ResponseEntity<?> getUsers(@RequestHeader(value = "authorization", required = false) String token) {
        try {
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            return AdminService.getUsers(userRepo);
        } catch (RequestException req) {
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<?> getOneUser(@RequestHeader(value = "authorization", required = false) String token, @PathVariable Long id) {
        try {
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            return AdminService.getOneUser(userRepo, id);
        } catch (RequestException req) {
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //PATCH
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateUser(@RequestHeader(value = "authorization", required = false) String token, @PathVariable Long id, @RequestBody UserInformationDto userDto) {
        try {
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            return AdminService.updateUser(userRepo, id, userDto);
        } catch (RequestException req) {
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/ban/{id}")
    public ResponseEntity<?> banUser(@RequestHeader(value = "authorization", required = false) String token, @PathVariable Long id) {
        try {
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            return AdminService.banUser(userRepo, id);
        } catch (RequestException req) {
            return new ResponseEntity<>(req.getMessage(),req.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/unban/{id}")
    public ResponseEntity<?> unbanUser(@RequestHeader(value = "authorization", required = false) String token, @PathVariable Long id) {
        try {
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            return AdminService.unbanUser(userRepo, id);
        } catch (RequestException req) {
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/rankings")
    public ResponseEntity<?> updateRankings(@RequestHeader(value = "authorization", required = false) String token, @RequestBody RankingsUpdateDto ranks) {
        try {
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            return AdminService.updateRankings(userRepo, ranks);
        } catch (RequestException req) {
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    ///DELETE
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@RequestHeader(value = "authorization", required = false) String token, @PathVariable Long id) {
        try {
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            return AdminService.deleteUser(userRepo, id);
        } catch (RequestException req) {
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
