package rest.validation;

import enums.UserRanks;
import org.springframework.http.HttpStatus;
import dto.user.*;
import dto.entities.*;
import exceptions.RequestException;
import models.User;
import repositories.UserRepo;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

@SuppressWarnings("all")

public class UserValidation {

    public static void Registration(UserRepo userRepo, ManagerInformationDto manager) throws RequestException {
        if(manager.getFullname() == null)
            throw new RequestException("Manager's name is missing", HttpStatus.UNPROCESSABLE_ENTITY);
        if(manager.getUsername() == null)
            throw new RequestException("Manager's username is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        if(manager.getPassword() == null)
            throw new RequestException("Manager's password is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        if(manager.getEmail() == null)
            throw new RequestException("Manager's email is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        if(manager.getPhoneNumber() == null)
            throw new RequestException("Manager's phone number is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        if(manager.getDateOfBirth() == null)
            throw new RequestException("Manager's date of birth is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        if(manager.getPassportNumber() == null)
            throw new RequestException("Manager's passport number is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        if(manager.getHotelname() == null)
            throw new RequestException("Manager's hotel name is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        if(manager.getDateOfEmployment() == null)
            throw new RequestException("Manager's date of employment is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        checkExisting(userRepo, manager.getUsername(), manager.getEmail());
        ParameterValidation.Register(manager);
    }

    public static void Registration(UserRepo userRepo, UserInformationDto user) throws RequestException{
        if(user.getFullname() == null)
            throw new RequestException("User's name is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        if(user.getUsername() == null)
            throw new RequestException("User's username is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        if(user.getPassword() == null)
            throw new RequestException("User's password is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        if(user.getEmail() == null)
            throw new RequestException("User's email is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        if(user.getPhoneNumber() == null)
            throw new RequestException("User's phone number is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        if(user.getDateOfBirth() == null)
            throw new RequestException("User's date of birth is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        if(user.getPassportNumber() == null)
            throw new RequestException("User's passport number is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        checkExisting(userRepo, user.getUsername(), user.getEmail());
        ParameterValidation.Register(user);
    }

    public static User Login(UserRepo userRepo, UserLoginDto user) throws RequestException{
        if(user.getEmail() == null)
            throw new RequestException("Email is missing",  HttpStatus.UNPROCESSABLE_ENTITY);
        if(user.getPassword() == null)
            throw new RequestException("Password is missing",  HttpStatus.UNPROCESSABLE_ENTITY);

        try {
            Optional<User> potentialUser = userRepo.findByEmail(user.getEmail());
            User userInDatabase = potentialUser.get();
            if(!userInDatabase.getPassword().equals(user.getPassword()))
                throw new RequestException("Invalid password", HttpStatus.BAD_REQUEST);
            if(userInDatabase.isBanned())
                throw new RequestException("You have been banned by the administrator", HttpStatus.FORBIDDEN);
            return userInDatabase;
        }catch (NoSuchElementException e){
            throw new RequestException("Invalid email", HttpStatus.BAD_REQUEST);
        }
    }

    public static void Update(UserInformationDto info, User user) throws RequestException{
        int count = 0;
        if(info.getFullname() != null) {
            user.setFullname(info.getFullname());
            count++;
        }
        if(info.getEmail() != null) {
            ParameterValidation.validateEmail(info.getEmail());
            user.setEmail(info.getEmail());
            count++;
        }
        if(info.getPhoneNumber() != null) {
            ParameterValidation.validatePhoneNumber(info.getPhoneNumber());
            user.setPhoneNumber(info.getPhoneNumber());
            count++;
        }
        if(info.getDateOfBirth() != null) {
            ParameterValidation.validateDate(info.getDateOfBirth());
            user.setDateOfBirth(info.getDateOfBirth());
            count++;
        }
        if(info.getUsername() != null){
            ParameterValidation.validateUsername(info.getUsername());
            user.setUsername(info.getUsername());
            count++;
        }
        if(info.getPassword() != null){
            ParameterValidation.validatePassword(info.getPassword());
            user.setPassword(info.getPassword());
            count++;
        }
        if(count == 0)
            throw new RequestException("Nothing to update", HttpStatus.BAD_REQUEST);
    }

    public static void Update(ManagerInformationDto info, User user) throws RequestException{
        int count = 0;
        if(info.getFullname() != null) {
            user.setFullname(info.getFullname());
            count++;
        }
        if(info.getEmail() != null) {
            ParameterValidation.validateEmail(info.getEmail());
            user.setEmail(info.getEmail());
            count++;
        }
        if(info.getPhoneNumber() != null) {
            ParameterValidation.validatePhoneNumber(info.getPhoneNumber());
            user.setPhoneNumber(info.getPhoneNumber());
            count++;
        }
        if(info.getDateOfBirth() != null) {
            ParameterValidation.validateDate(info.getDateOfBirth());
            user.setDateOfBirth(info.getDateOfBirth());
            count++;
        }
        if(info.getUsername() != null){
            ParameterValidation.validateUsername(info.getUsername());
            user.setUsername(info.getUsername());
            count++;
        }
        if(info.getPassword() != null){
            ParameterValidation.validatePassword(info.getPassword());
            user.setPassword(info.getPassword());
            count++;
        }
        if(info.getHotelname() != null){
            user.setHotelname(info.getHotelname());
            count++;
        }
        if(info.getDateOfEmployment() != null){
            ParameterValidation.validateDate(info.getDateOfBirth());
            user.setDateOfEmployment(info.getDateOfEmployment());
            count++;
        }
        if(count == 0)
            throw new RequestException("Nothing to update", HttpStatus.BAD_REQUEST);
    }

    public static void Rankings(RankingsUpdateDto ranks) throws RequestException{
        int count = 0;
        if(ranks.getSilverPoints() != null)
            count++;
        if(ranks.getGoldPoints() != null)
            count++;
        if(count == 1)
            throw new RequestException("Invalid parameters, need both silver and gold points", HttpStatus.BAD_REQUEST);
        if(count == 0)
            throw new RequestException("Invalid parameters, silver and gold rankings are missing", HttpStatus.BAD_REQUEST);
        if(ranks.getSilverPoints() == UserRanks.SILVERNUMBER && ranks.getGoldPoints() == UserRanks.GOLDNUMBER)
            throw new RequestException("Invalid parameters, current ranking system is the same", HttpStatus.BAD_REQUEST);
        if(ranks.getSilverPoints() > ranks.getGoldPoints())
            throw new RequestException("Silver points cannot be greater than gold points", HttpStatus.BAD_REQUEST);
    }

    private static void checkExisting(UserRepo userRepo, String username, String email)throws RequestException{
        try{
            Optional<User> user = userRepo.findByUsername(username);
            user.get();
            throw new RequestException("Username is already in use",  HttpStatus.BAD_REQUEST);
        }catch (NoSuchElementException e){
            try {
                Optional<User> user = userRepo.findByEmail(email);
                user.get();
                throw new RequestException("Email is already in use",  HttpStatus.BAD_REQUEST);
            }catch (NoSuchElementException ignored){ }
        }
    }




    public static class ParameterValidation {

          private static final ArrayList<String> allowedEmails = new ArrayList<>(Arrays.asList("gmail.com", "hotmail.com", "yahoo.com", "raf.rs"));


          private static void Register(UserInformationDto newUser) throws RequestException {
              if(newUser.getUsername().equals(newUser.getPassword()))
                  throw new RequestException("Username and password cannot be the same", HttpStatus.BAD_REQUEST);
              validateUsername(newUser.getUsername());
              validatePassword(newUser.getPassword());
              validatePhoneNumber(newUser.getPhoneNumber());
              validatePassportNumber(newUser.getPassportNumber());
              validateDate(newUser.getDateOfBirth());
              validateEmail(newUser.getEmail());
          }

          private static void Register(ManagerInformationDto newManager) throws RequestException{
              if(newManager.getUsername().equals(newManager.getPassword()))
                  throw new RequestException("Username and password cannot be the same", HttpStatus.BAD_REQUEST);
              validateUsername(newManager.getUsername());
              validatePassword(newManager.getPassword());
              validatePassportNumber(newManager.getPassportNumber());
              validatePhoneNumber(newManager.getPhoneNumber());
              validateDate(newManager.getDateOfBirth());
              validateDate(newManager.getDateOfEmployment());
              validateEmail(newManager.getEmail());
          }


          private static void validateUsername(String username) throws RequestException{
            boolean containsForbidden = false;
            if(username.length() < 5)
                throw new RequestException("Username must be at least 5 characters", HttpStatus.BAD_REQUEST);
            for( int i = 0; i < username.length(); i++ ){
                char c = username.charAt(i);
                if (!((c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122))) {
                    containsForbidden = true;
                    break;
                }
            }
            if(containsForbidden)
                throw new RequestException("Username can only consist of letters and numbers", HttpStatus.BAD_REQUEST);
        }

          public static void validatePassword(String password) throws RequestException {
            if (password.length() < 8)
                throw new RequestException("Password must be at least 8 characters", HttpStatus.BAD_REQUEST);
            if (password.length() > 20)
                throw new RequestException("Password can have a maximum of 20 characters", HttpStatus.BAD_REQUEST);
            int count = 1;
            char previousCharacter = password.charAt(0);
            for (int i = 1; i < password.length(); i++) {
                char c = password.charAt(i);
                if (c == previousCharacter)
                    count++;
                previousCharacter = c;
            }
            if (count == password.length())
                throw new RequestException("Password cannot consist of same letters", HttpStatus.BAD_REQUEST);
            boolean hasCaps = false;
            boolean hasNumbers = false;
            boolean hasForbidden = false;
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (c >= 47 && c <= 57)
                    hasNumbers = true;
                else if (c >= 65 && c <= 90)
                    hasCaps = true;
                else if (!(c >= 97 && c <= 122)) {
                    hasForbidden = true;
                }
            }
            if (hasForbidden)
                throw new RequestException("Password can consist only of letters and numbers", HttpStatus.BAD_REQUEST);
            if (!hasNumbers && !hasCaps)
                throw new RequestException("Password must contain at least one uppercase letter and one number", HttpStatus.BAD_REQUEST);
            if (!hasNumbers)
                throw new RequestException("Password must contain at least one number", HttpStatus.BAD_REQUEST);
            if (!hasCaps)
                throw new RequestException("Password must contain at least one uppercase letter", HttpStatus.BAD_REQUEST);
        }

          private static void validatePassportNumber(String passport) throws RequestException{
            if(passport.length() != 9)
                throw new RequestException("Passport number must be exactly 9 numbers",HttpStatus.BAD_REQUEST);
            for( int i = 1; i < passport.length(); i++ ){
                char c = passport.charAt(i);
                if(!(c >= 48 && c <= 57))
                    throw new RequestException("Passport can consist only of numbers", HttpStatus.BAD_REQUEST);
            }
        }

          private static void validateDate(String date) throws RequestException{
            if(date.length()!=10)
                throw new RequestException("Invalid date, format dd/mm/yyyy", HttpStatus.BAD_REQUEST);
            int count = 0;
            for (int i = 0; i < date.length(); i++){
                if(date.charAt(i) == 47)
                    count++;
            }
            if(count != 2)
                throw new RequestException("Invalid date, format dd/mm/yyyy", HttpStatus.BAD_REQUEST);
            String[] dates = date.split("/");
            try{
                int day = Integer.parseInt(dates[0]);
                int month = Integer.parseInt(dates[1]);
                int year = Integer.parseInt(dates[2]);
                checkDates(day, month, year);
            }catch (NumberFormatException e){
                throw new RequestException("Invalid date, format dd/mm/yyyy",HttpStatus.BAD_REQUEST);
            }
        }

          private static void validateEmail(String email) throws RequestException{
            int length = email.length();
            int index = 0, count = 0;
            for(int i = 0; i < length;i++){
                if(email.charAt(i) == '@') {
                    count++;
                    index = i;
                }
            }
            if(count > 1)
                throw new RequestException("Email cannot contain multiple @ symbols", HttpStatus.BAD_REQUEST);
            String afterEmail = email.substring(index + 1);
            if(!allowedEmails.contains(afterEmail))
                throw new RequestException("Disallowed email, allowed emails: " + allowedEmails.toString(), HttpStatus.BAD_REQUEST);
        }

        private static void validatePhoneNumber(String phoneNumber) throws RequestException {
            int length = phoneNumber.length();
            if (length < 5)
                throw new RequestException("Phone number too short", HttpStatus.BAD_REQUEST);
            if (length > 15)
                throw new RequestException("Phone number too long", HttpStatus.BAD_REQUEST);
            for (int i = 0; i < length; i++) {
                char c = phoneNumber.charAt(i);
                if (!(c >= 48 && c <= 57))
                    throw new RequestException("Phone number can consist only of numbers", HttpStatus.BAD_REQUEST);
            }
        }

          private static void checkDates(int day, int month, int year) throws RequestException {
              if(year < 1900)
                  throw new RequestException("Minimum value for a year is 1900", HttpStatus.BAD_REQUEST);
              if(year > Year.now().getValue())
                  throw new RequestException("You cannot live in the future", HttpStatus.BAD_REQUEST);
              if(month > 12 || month < 1)
                  throw new RequestException("Month can only be between 1 and 12", HttpStatus.BAD_REQUEST);
              if(day < 1)
                  throw new RequestException("Day value must be at least 1", HttpStatus.BAD_REQUEST);
              if(day > getMonthDays(month, year))
                  throw new RequestException("Day doesn't match month's number of days in given year", HttpStatus.BAD_REQUEST);
          }

          private static int getMonthDays (int month, int year){
              switch (month){
                  case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                      return 31;
                  case 2:
                      if (year % 4 == 0) {
                          if (year % 100 == 0) {
                              if (year % 400 == 0)
                                  return 29;
                              else
                                  return 28;
                          }
                          else
                              return 29;
                      }
                      else
                          return 28;
                  case 4: case 6: case 9: case 11:
                      return 30;

              }
              return -1;
          }

    }

}
