package enums;

import exceptions.RequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationTypes {


    public static final String ACCOUNT_ACTIVATION = "Account Activation";
    public static final String PASSWORD_RESET = "Password Reset";
    public static final String RESERVATION_CONFIRMED = "Reservation Confirmed";
    public static final String RESERVATION_CANCELLED = "Reservation Cancelled";
    public static final String RESERVATION_REMINDER = "Reservation Reminder";
    public static final String MANAGER_RESERVATION_CONFIRMED = "Manager Reservation Confirmed";
    public static final String MANAGER_RESERVATION_CANCELLED = "Manager Reservation Cancelled";

    public static String getMail(String type, String username, String hotelName){
        switch (type){
            case ACCOUNT_ACTIVATION:
                return "Successfully registered, username: " + username;
            case PASSWORD_RESET:
                return "Successfully changed your password";
            case RESERVATION_CONFIRMED:
                return "Successfully made a reservation at hotel: " + hotelName;
            case RESERVATION_CANCELLED:
                return "Successfully cancelled your reservation at hotel: " + hotelName;
            case RESERVATION_REMINDER:
                return "Reminder: Your reservation at hotel: " + hotelName + " is in two days";
            case MANAGER_RESERVATION_CANCELLED:
                return "User: " + username + " has cancelled a reservation at your hotel";
            case MANAGER_RESERVATION_CONFIRMED:
                return "User: " + username + " has made a reservation at your hotel";
            default:
                return "";
        }
    }

}
