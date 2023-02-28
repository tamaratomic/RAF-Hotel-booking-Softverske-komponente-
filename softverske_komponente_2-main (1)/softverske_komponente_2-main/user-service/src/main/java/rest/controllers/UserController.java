package rest.controllers;

import dto.user.ManagerInformationDto;
import dto.user.UserInformationDto;
import dto.user.UserLoginDto;
import exceptions.RequestException;
import middleware.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import repositories.UserRepo;
import rest.services.UserService;

import java.util.NoSuchElementException;

@SuppressWarnings("all")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JmsTemplate jmsTemplate;

    ///  AUTH
    @PostMapping(value = "/register")
    public ResponseEntity<String> registerUser(@RequestBody UserInformationDto userDto) {
        try {
            return UserService.registerUser(userRepo, userDto, jmsTemplate);
        } catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userDto) {
        try {
            return UserService.loginUser(userRepo, userDto);
        } catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        } catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/manager/register")
    public ResponseEntity<String> registerManager(@RequestBody ManagerInformationDto managerDto) {
        try {
            return UserService.registerManager(userRepo, managerDto, jmsTemplate);
        } catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(),req.getStatus());
        } catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///GET
    @GetMapping(value = "/getself/{id}")
    public ResponseEntity<?> fetchSelfInfo(@RequestHeader(value = "authorization", required = false) String token, @PathVariable Long id){
        try{
          Authorization.checkSelfUpdate(Authorization.checkAuthorization(token), id);
          return UserService.fetchSelfInfo(userRepo, id);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///PUT
    @PutMapping(value = "/password/{id}")
    public ResponseEntity<?> changePassword(@RequestHeader(value = "authorization", required = false) String token,
                                            @RequestHeader(value = "new-password") String password,
                                            @PathVariable Long id){
        try {
            Authorization.checkSelfUpdate(Authorization.checkAuthorization(token), id);
            return UserService.changePassword(userRepo, id, password, jmsTemplate);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(),req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/selfupdate/{id}")
    public ResponseEntity<?> selfUpdate(@RequestHeader(value = "authorization", required = false) String token, @PathVariable Long id, @RequestBody UserInformationDto userDto){
        try {
            Authorization.checkSelfUpdate(Authorization.checkAuthorization(token), id);
            return UserService.selfUpdateUser(userRepo, userDto, id);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(),req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/selfupdate/manager/{id}")
    public ResponseEntity<?> selfUpdateManager(@RequestHeader(value = "authorization", required = false) String token, @PathVariable Long id, @RequestBody ManagerInformationDto managerDto) {
        try {
            Authorization.checkSelfUpdate(Authorization.checkAuthorization(token), id);
            return UserService.selfUpdateManager(userRepo, managerDto, id);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(),req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/points/{id}")
    public ResponseEntity<?> adjustPoints(@RequestHeader(value = "authorization", required = false) String token, @PathVariable Long id,
                                          @RequestHeader(value = "action-type") String action,
                                          @RequestHeader(value = "manager-id") Long managerId,
                                          @RequestHeader(value = "hotel-name") String hotelName){
        try{
           Authorization.checkSelfUpdate(Authorization.checkAuthorization(token), id);
           return UserService.adjustPoints(userRepo, id, action, managerId, jmsTemplate, hotelName);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/service/get/email/{id}")
    public ResponseEntity<?> getEmail(@RequestHeader(value = "key", required = false)String key,  @PathVariable Long id){
          try{
             if(!key.equals("37fe66b4-e44b-48e7-932b-4196483786c0"))
                 return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
             return new ResponseEntity<>(userRepo.findById(id).get().getEmail(), HttpStatus.OK);
          }catch (NoSuchElementException e){
              return new ResponseEntity<>("No such user", HttpStatus.BAD_REQUEST);
          }catch (Exception e){
              return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
          }
    }

}