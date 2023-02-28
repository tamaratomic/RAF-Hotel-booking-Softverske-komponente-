package rest.services;

import dto.entities.RankingsUpdateDto;
import dto.user.UserInformationDto;
import enums.UserRanks;
import enums.UserTypes;
import exceptions.RequestException;
import models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import repositories.UserRepo;
import rest.validation.UserValidation;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@SuppressWarnings("all")

public class AdminService {

    public static ResponseEntity<?> getUsers(UserRepo userRepo) throws RequestException {
        List<User> allUsers = userRepo.findAll();
        if(allUsers.isEmpty())
            throw new RequestException("No users are currently in the database", HttpStatus.ACCEPTED);
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    public static ResponseEntity<User> getOneUser(UserRepo userRepo, Long id) throws RequestException{
        try{
            User user = userRepo.findById(id).get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (NoSuchElementException e){
            throw new RequestException("Requested user is not in the database", HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<String> updateUser (UserRepo userRepo, Long id, UserInformationDto userDto) throws RequestException{
        try {
            User user = userRepo.findById(id).get();
            UserValidation.Update(userDto, user);
            user.setLastModified(Instant.now());
            userRepo.save(user);
            return new ResponseEntity<>("Successfully updated user", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Invalid user id", HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<String> banUser(UserRepo userRepo, Long id) throws RequestException{
        try {
            User userTobeBanned = userRepo.findById(id).get();
            if (userTobeBanned.isBanned())
                throw new RequestException("User is already banned", HttpStatus.BAD_REQUEST);
            if (userTobeBanned.getUserType().equals(UserTypes.ADMINISTRATOR))
                throw new RequestException("Cannot ban administrators", HttpStatus.BAD_REQUEST);
            userTobeBanned.setBanned(true);
            userTobeBanned.setLastModified(Instant.now());
            userRepo.save(userTobeBanned);
            return new ResponseEntity<>("Successfully banned user, username: " + userTobeBanned.getUsername(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new RequestException("Requested user is not in the database", HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<String> unbanUser(UserRepo userRepo, Long id) throws RequestException{
        try {
            User userTobeUnbanned = userRepo.findById(id).get();
            if (!userTobeUnbanned.isBanned())
                throw new RequestException("User is not banned", HttpStatus.BAD_REQUEST);
            userTobeUnbanned.setBanned(false);
            userTobeUnbanned.setLastModified(Instant.now());
            userRepo.save(userTobeUnbanned);
            return new ResponseEntity<>("Successfully unbanned user, username: " + userTobeUnbanned.getUsername(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new RequestException("Requested user is not in the database", HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<String> updateRankings(UserRepo userRepo, RankingsUpdateDto ranks) throws RequestException{
        UserValidation.Rankings(ranks);
        UserRanks.setSILVERNUMBER(ranks.getSilverPoints());
        UserRanks.setGOLDNUMBER(ranks.getGoldPoints());
        List<User> allusers = userRepo.findAll();
        int i = 0;
        for(User user: allusers){
            user.setRank(UserRanks.determineRank(user.getNumberOfreservations()));
            allusers.set(i, user);
            i++;
        }
        userRepo.saveAll(allusers);
        return new ResponseEntity<>("Successfully updated rankings system", HttpStatus.OK);
    }

    public static ResponseEntity<String> deleteUser(UserRepo userRepo, Long id) throws RequestException{
        try {
            User userToDelete = userRepo.findById(id).get();
            if(userToDelete.getUserType().equals(UserTypes.ADMINISTRATOR))
                throw new RequestException("Cannot delete administrator accounts", HttpStatus.BAD_REQUEST);
            userRepo.deleteById(id);
            return new ResponseEntity<>("Successfully deleted user: " + userToDelete.getUsername(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new RequestException("Requested user is not in the database", HttpStatus.BAD_REQUEST);
        }
    }


}
