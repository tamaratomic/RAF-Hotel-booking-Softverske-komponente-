package rest.controllers;

import api.ApiCommon;
import exceptions.RequestException;
import middleware.Authorization;
import models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.NotificationRepo;

import java.util.NoSuchElementException;

@SuppressWarnings("all")

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepo notificationRepo;

    @GetMapping(value = "/get")
    public ResponseEntity<?> getAll(@RequestHeader(value = "authorization",required = false) String token,
                                    @RequestHeader(value = "notification-type", required = false) String notificationType){
        try{
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            if(notificationType == null)
               return new ResponseEntity<>(notificationRepo.findAll(), HttpStatus.OK);
            return new ResponseEntity<>(notificationRepo.findByType(notificationType), HttpStatus.OK);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<?> getOne(@RequestHeader(value = "authorization",required = false) String token, @PathVariable Long id){
        try{
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            try{
                Notification notification = notificationRepo.findById(id).get();
                return new ResponseEntity<>(notification, HttpStatus.OK);
            }catch (NoSuchElementException e){return new ResponseEntity<>("No such notification",HttpStatus.BAD_REQUEST);}
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteOne(@RequestHeader(value = "authorization",required = false) String token, @PathVariable Long id){
        try{
            Authorization.checkAdministrator(Authorization.checkAuthorization(token));
            try{
                Notification notification = notificationRepo.findById(id).get();
                notificationRepo.deleteById(notification.getId());
                return new ResponseEntity<>("Successfully deleted notification", HttpStatus.OK);
            }catch (NoSuchElementException e){return new ResponseEntity<>("No such notification",HttpStatus.BAD_REQUEST);}
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/users/get")
    public ResponseEntity<?> getMyNotifications(@RequestHeader(value = "authorization",required = false) String token,
                                                @RequestHeader(value = "notification-type", required = false) String notificationType){
        try{
            Authorization.checkAuthorization(token);
            if(notificationType == null)
                return new ResponseEntity<>(notificationRepo.findByRecipient(ApiCommon.getFromToken(token).getEmail()), HttpStatus.OK);
            return new ResponseEntity<>(notificationRepo.findByRecipientAndType(ApiCommon.getFromToken(token).getEmail(), notificationType), HttpStatus.OK);
        }catch (RequestException req){
            return new ResponseEntity<>(req.getMessage(), req.getStatus());
        }catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
