package rest.services;

import dto.entities.NotificationDto;
import dto.user.*;
import enums.NotificationTypes;
import exceptions.RequestException;
import mappers.UserMapper;
import middleware.JsonWebToken;
import models.Notification;
import models.User;
import enums.UserRanks;
import enums.UserTypes;
import org.springframework.jms.core.JmsTemplate;
import repositories.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rest.validation.UserValidation;
import java.time.Instant;
import java.util.NoSuchElementException;

@SuppressWarnings("all")

public class UserService {

    ///AUTH
    public static ResponseEntity<String> registerUser(UserRepo userRepo, UserInformationDto userDto, JmsTemplate jmsTemplate) throws RequestException {
        UserValidation.Registration(userRepo, userDto);
        User newUser = UserMapper.fromUserInformation(userDto);
        userRepo.save(newUser);
        String token = JsonWebToken.createToken(newUser.getId(), newUser.getUsername(), newUser.getRank(),
                newUser.getEmail(), newUser.getUserType(), newUser.isBanned());
        NotificationDto notificationDto = new NotificationDto(newUser.getEmail(), NotificationTypes.ACCOUNT_ACTIVATION, newUser.getUsername(), null);
        jmsTemplate.convertAndSend("account-activation", notificationDto);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    public static ResponseEntity<String> registerManager(UserRepo userRepo, ManagerInformationDto managerDto,JmsTemplate jmsTemplate) throws RequestException{
        UserValidation.Registration(userRepo, managerDto);
        User newManager = UserMapper.fromManagerInformation(managerDto);
        userRepo.save(newManager);
        String token = JsonWebToken.createToken(newManager.getId(), newManager.getUsername(), newManager.getRank(),
                newManager.getEmail(), newManager.getUserType(), newManager.isBanned());
        NotificationDto notificationDto = new NotificationDto(newManager.getEmail(), NotificationTypes.ACCOUNT_ACTIVATION, newManager.getUsername(), null);
        jmsTemplate.convertAndSend("account-activation", notificationDto);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    public static ResponseEntity<String> loginUser(UserRepo userRepo, UserLoginDto userDto) throws RequestException{
            User user = UserValidation.Login(userRepo, userDto);
            String token = JsonWebToken.createToken(user.getId(), user.getUsername(), user.getRank(),
                    user.getEmail(), user.getUserType(), user.isBanned());
            return new ResponseEntity<>(token, HttpStatus.OK);
    }


    ///USER ACTIONS

    public static ResponseEntity<?> selfUpdateUser(UserRepo userRepo, UserInformationDto userDto, Long id) throws RequestException{
        try {
            User user = userRepo.findById(id).get();
            UserValidation.Update(userDto, user);
            user.setLastModified(Instant.now());
            userRepo.save(user);
            return new ResponseEntity<>("Successfully update info", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Invalid user id", HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<?> selfUpdateManager(UserRepo userRepo, ManagerInformationDto managerDto, Long id) throws RequestException{
        try {
            User user = userRepo.findById(id).get();
            UserValidation.Update(managerDto, user);
            user.setLastModified(Instant.now());
            userRepo.save(user);
            return new ResponseEntity<>("Successfully update info", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Invalid user id", HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<?> fetchSelfInfo(UserRepo userRepo, Long id) throws RequestException{
        try{
            User user = userRepo.findById(id).get();
            if(user.getUserType() == UserTypes.MANAGER){
                ManagerInformationDto dto = UserMapper.fromManager(user);
                return new ResponseEntity<>(dto, HttpStatus.OK);
            }
            UserInformationDto dto = UserMapper.fromUser(user);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("Invalid user id", HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<?> changePassword(UserRepo userRepo, Long id, String newPassword, JmsTemplate jmsTemplate) throws RequestException{
        try{
            User user = userRepo.findById(id).get();
            UserValidation.ParameterValidation.validatePassword(newPassword);
            if(user.getPassword().equals(newPassword))
                throw new RequestException("New password is the same as the old password", HttpStatus.BAD_REQUEST);
            user.setPassword(newPassword);
            userRepo.save(user);
            NotificationDto notificationDto = new NotificationDto(user.getEmail(), NotificationTypes.PASSWORD_RESET, null, null);
            jmsTemplate.convertAndSend("password-change", notificationDto);
            return new ResponseEntity<>("Successfully updated password", HttpStatus.OK);
        }catch (NoSuchElementException e){
            throw new RequestException("No such user in the database",HttpStatus.BAD_REQUEST);
        }
    }


    ///Reservation

    public static ResponseEntity<?> adjustPoints(UserRepo userRepo, Long id, String action, Long managerId,
                                                 JmsTemplate jmsTemplate, String hotelName) throws RequestException{
        try{
            String destination;
            User user = userRepo.findById(id).get();
            NotificationDto userNotification = new NotificationDto(user.getEmail(), "", null, hotelName);
            NotificationDto managerNotification = new NotificationDto(user.getEmail(), "", user.getUsername(), null);
          if(action.equals(UserRanks.MAKE_RESERVATION)) {
              managerNotification.setSubject(NotificationTypes.MANAGER_RESERVATION_CONFIRMED);
              managerNotification.setText(NotificationTypes.getMail(managerNotification.getSubject(), managerNotification.getUsername(), null));
              userNotification.setSubject(NotificationTypes.RESERVATION_CONFIRMED);
              userNotification.setText(NotificationTypes.getMail(userNotification.getSubject(), null, hotelName));
              destination = "reservation-cancelled";
              user.setNumberOfReservations(user.getNumberOfreservations() + 1);
          }else if(action.equals(UserRanks.CANCEL_RESERVATION)) {
              managerNotification.setSubject(NotificationTypes.MANAGER_RESERVATION_CANCELLED);
              managerNotification.setText(NotificationTypes.getMail(managerNotification.getSubject(), managerNotification.getUsername(), null));
              userNotification.setSubject(NotificationTypes.RESERVATION_CANCELLED);
              userNotification.setText(NotificationTypes.getMail(userNotification.getSubject(), null, hotelName));
              destination = "reservation-confirmation";
              user.setNumberOfReservations(user.getNumberOfreservations() - 1);
          }else
              throw new RequestException("Invalid adjustment parameter", HttpStatus.BAD_REQUEST);
          user.setLastModified(Instant.now());
          user.setRank(UserRanks.determineRank(user.getNumberOfreservations()));
          userRepo.save(user);
          try{
             String managerEmail = userRepo.findById(managerId).get().getEmail();
             managerNotification.setRecipient(managerEmail);
             jmsTemplate.convertAndSend(destination, userNotification);
             jmsTemplate.convertAndSend(destination, managerNotification);
          }catch (NoSuchElementException ignored){}
          return new ResponseEntity<>("Successfully adjusted points", HttpStatus.OK);
        }catch (NoSuchElementException e){
            throw new RequestException("Invalid user id", HttpStatus.BAD_REQUEST);
        }
    }


}
