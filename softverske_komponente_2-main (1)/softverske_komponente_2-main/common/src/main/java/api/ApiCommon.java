package api;

import com.google.gson.Gson;
import dto.entities.TokenDto;
import middleware.JsonWebToken;
public class ApiCommon {

    //Users
    public static final String USER_REGISTER = "http://localhost:8000/users/register";
    public static final String MANAGER_REGISTER = "http://localhost:8000/users/manager/register";
    public static final String LOGIN = "http://localhost:8000/users/login";
    public static final String GET_SELF_INFO = "http://localhost:8000/users/getself";
    public static final String UPDATE_SELF_INFO = "http://localhost:8000/users/selfupdate";
    public static final String GET_HOTELS = "http://localhost:8000/hotels/users/getall";
    public static final String REVIEW_HOTEL = "http://localhost:8000/hotels/users/review";
    public static final String DELETE_REVIEW = "http://localhost:8000/hotels/users/review/delete";
    public static final String EDIT_REVIEW = "http://localhost:8000/hotels/users/review/edit";
    public static final String MAKE_RESERVATION = "http://localhost:8000/reservations/users/make";
    public static final String GET_MY_RESERVATIONS = "http://localhost:8000/reservations/users/get";
    public static final String CANCEL_RESERVATION = "http://localhost:8000/reservations/users/cancel";
    public static final String GET_MY_NOTIFICATIONS = "http://localhost:8000/notifications/users/get";
    public static final String CHANGE_PASSWORD = "http://localhost:8000/users/password";

    ///Admin
    public static final String GET_USERS = "http://localhost:8000/users/get";
    public static final String RANKINGS = "http://localhost:8000/rankings";
    public static final String BAN_USER = "http://localhost:8000/users/ban";
    public static final String UNBAN_USER = "http://localhost:8000/users/unban";
    public static final String DELETE_USER = "http://localhost:8000/users/delete";
    public static final String UPDATE_USER = "http://localhost:8000/users/update";
    public static final String GET_ALL_HOTELS = "http://localhost:8000/hotels/admin/getall";
    public static final String GET_ONE_HOTEL = "http://localhost:8000/hotels/admin/get";
    public static final String UPDATE_ONE_HOTEL = "http://localhost:8000/hotels/admin/update";
    public static final String DELETE_ONE_HOTEL = "http://localhost:8000/hotels/admin/delete";
    public static final String GET_RESERVATIONS = "http://localhost:8000/reservations/get";
    public static final String GET_ONE_RESERVATION = "http://localhost:8000/reservations/get";
    public static final String DELETE_RESERVATION = "http://localhost:8000/reservations/delete";
    public static final String GET_ALL_NOTIFICATIONS = "http://localhost:8000/notifications/get";
    public static final String GET_ONE_NOTIFICATION = "http://localhost:8000/notifications/get";
    public static final String DELETE_NOTIFICATION = "http://localhost:8000/notifications/delete";

    //Manager
    public static final String GET_MANAGER_HOTEL = "http://localhost:8000/hotels/manager/myhotel";
    public static final String EDIT_MANAGER_HOTEL = "http://localhost:8000/hotels/manager/myhotel/edit";
    public static final String ADD_MANAGER_HOTEL = "http://localhost:8000/hotels/manager/myhotel/add";
    public static final String EDIT_MANAGER_HOTEL_ROOMS = "http://localhost:8000/hotels/manager/myhotel/edit/rooms";

    //Services
    public static final String ADJUST_POINTS = "http://localhost:8000/users/points";
    public static final String GET_SERVICE_RESERVATIONS = "http://localhost:8000/reservations/service/get";
    public static final String GET_USER_EMAIL = "http://localhost:8000/users/service/get/email";

    public static TokenDto getFromToken(String token){
        return new Gson().fromJson(JsonWebToken.decodeToken(token).getSubject(), TokenDto.class);
    }
}
