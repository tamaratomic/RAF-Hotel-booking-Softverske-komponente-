package mappers;
import dto.user.ManagerInformationDto;
import dto.user.UserInformationDto;
import dto.user.UserLoginDto;
import enums.UserTypes;
import models.User;


@SuppressWarnings("all")
public class UserMapper {

    public static User fromUserInformation(UserInformationDto dto){
        User user = new User();
        user.setFullname(dto.getFullname());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setPassportNumber(dto.getPassportNumber());
        return user;
    }

    public static User fromManagerInformation(ManagerInformationDto dto){
        User manager = new User();
        manager.setFullname(dto.getFullname());
        manager.setUsername(dto.getUsername());
        manager.setPassword(dto.getPassword());
        manager.setEmail(dto.getEmail());
        manager.setPhoneNumber(dto.getPhoneNumber());
        manager.setDateOfBirth(dto.getDateOfBirth());
        manager.setPassportNumber(dto.getPassportNumber());
        manager.setHotelname(dto.getHotelname());
        manager.setDateOfEmployment(dto.getDateOfEmployment());
        manager.setUserType(UserTypes.MANAGER);
        return manager;
    }

    public static UserInformationDto fromUser(User user){
        UserInformationDto userInfo = new UserInformationDto();
        userInfo.setFullname(user.getFullname());
        userInfo.setUsername(user.getUsername());
        userInfo.setPassword(user.getPassword());
        userInfo.setEmail(user.getEmail());
        userInfo.setPhoneNumber(user.getPhoneNumber());
        userInfo.setPassportNumber(user.getPassportNumber());
        userInfo.setDateOfBirth(user.getDateOfBirth());
        return userInfo;
    }

    public static ManagerInformationDto fromManager(User user){
        ManagerInformationDto userInfo = new ManagerInformationDto();
        userInfo.setFullname(user.getFullname());
        userInfo.setUsername(user.getUsername());
        userInfo.setPassword(user.getPassword());
        userInfo.setEmail(user.getEmail());
        userInfo.setPhoneNumber(user.getPhoneNumber());
        userInfo.setPassportNumber(user.getPassportNumber());
        userInfo.setDateOfBirth(user.getDateOfBirth());
        userInfo.setHotelname(user.getHotelname());
        userInfo.setDateOfEmployment(user.getDateOfEmployment());
        return userInfo;
    }

    public static UserInformationDto fromScratch(String fullname, String username, String password,
                                                 String email, String phoneNumber, String passportNumber,
                                                 String dateOfBirth){
        UserInformationDto info = new UserInformationDto();
        info.setFullname(fullname);
        info.setUsername(username);
        info.setPassword(password);
        info.setEmail(email);
        info.setPhoneNumber(phoneNumber);
        info.setPassportNumber(passportNumber);
        info.setDateOfBirth(dateOfBirth);
        return info;
    }

    public static ManagerInformationDto fromScratch(String fullname, String username, String password,
                                                 String email, String phoneNumber, String passportNumber,
                                                 String dateOfBirth, String hotelname, String dateOfEmployment){
        ManagerInformationDto info = new ManagerInformationDto();
        info.setFullname(fullname);
        info.setUsername(username);
        info.setPassword(password);
        info.setEmail(email);
        info.setPhoneNumber(phoneNumber);
        info.setPassportNumber(passportNumber);
        info.setDateOfBirth(dateOfBirth);
        info.setHotelname(hotelname);
        info.setDateOfEmployment(dateOfEmployment);
        return info;
    }


    public static UserLoginDto Login(String email, String password){
        UserLoginDto loginInfo = new UserLoginDto();
        loginInfo.setEmail(email);
        loginInfo.setPassword(password);
        return loginInfo;
    }

}
