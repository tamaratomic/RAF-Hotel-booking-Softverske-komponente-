package api.admin;

import api.ApiCommon;
import dto.hotels.HotelInformationDto;
import exceptions.ClientRequestException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import dto.entities.RankingsUpdateDto;
import dto.user.UserInformationDto;
import models.Hotel;
import models.Notification;
import models.Reservation;
import models.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("all")
public class AdminApi {

    public static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    public static List<User> getUsers(String token)throws IOException,ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_USERS);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("authorization", token);
        HttpResponse res = client.execute(get);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200) //
            throw new ClientRequestException(responseString);
        return mapper.readValue(responseString,  new TypeReference<List<User>>(){});
    }

    public static User getUser(Long id, String token) throws IOException, ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_USERS + "/" + id);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("authorization", token);
        HttpResponse res = client.execute(get);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return mapper.readValue(responseString, User.class);
    }

    public static String banUser(Long id, String token) throws IOException, ClientRequestException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(ApiCommon.BAN_USER + "/" + id);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("authorization", token);
        HttpResponse res = client.execute(put);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200) //
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static String unbanUser(Long id, String token) throws IOException, ClientRequestException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(ApiCommon.UNBAN_USER + "/" + id);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("authorization", token);
        HttpResponse res = client.execute(put);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200) //
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static String updateRankings(RankingsUpdateDto rankingsUpdateDto, String token) throws IOException, ClientRequestException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(ApiCommon.RANKINGS);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("authorization", token);
        put.setEntity(new StringEntity(new Gson().toJson(rankingsUpdateDto, RankingsUpdateDto.class), "UTF-8"));
        HttpResponse res = client.execute(put);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static String updateUser(Long id,String token, @Nullable String fullname,@Nullable String username,@Nullable String password,
                                        @Nullable String email, @Nullable String phoneNumber,
                                        @Nullable String dateOfBirth, @Nullable String passportNumber) throws IOException, ClientRequestException{
       UserInformationDto info = makeUserInfo(fullname, username, password, email, phoneNumber, dateOfBirth, passportNumber);
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(ApiCommon.UPDATE_USER + "/" + id);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("authorization", token);
        put.setEntity(new StringEntity(new Gson().toJson(info, UserInformationDto.class), "UTF-8"));
        HttpResponse res = client.execute(put);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static String deleteUser(Long id, String token) throws IOException, ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpDelete delete = new HttpDelete(ApiCommon.DELETE_USER + "/" + id);
        delete.setHeader("Content-Type", "application/json");
        delete.setHeader("authorization", token);
        HttpResponse res = client.execute(delete);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
           throw new ClientRequestException(responseString);
        return responseString;
    }

    private static UserInformationDto makeUserInfo(@Nullable String fullname,@Nullable String username,@Nullable String password,
                                                   @Nullable String email, @Nullable String phoneNumber,
                                                   @Nullable String dateOfBirth, @Nullable String passportNumber){
        UserInformationDto info = new UserInformationDto();
        if(fullname != null)
            info.setFullname(fullname);
        if(username != null)
            info.setUsername(username);
        if(password != null)
            info.setPassword(password);
        if(email != null)
            info.setEmail(email);
        if(phoneNumber != null)
            info.setPhoneNumber(phoneNumber);
        if(dateOfBirth != null)
            info.setUsername(dateOfBirth);
        if(passportNumber != null)
            info.setPassportNumber(passportNumber);
        return info;
    }

    public static List<Hotel> getHotels(String token) throws IOException, ClientRequestException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_ALL_HOTELS);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("authorization", token);
        HttpResponse res = client.execute(get);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200) //
            throw new ClientRequestException(responseString);
        return mapper.readValue(responseString,  new TypeReference<List<Hotel>>(){});
    }

    public static Hotel getHotel(String token, Long hotelId) throws IOException, ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_ONE_HOTEL + "/" + hotelId);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("authorization", token);
        HttpResponse res = client.execute(get);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200) //
            throw new ClientRequestException(responseString);
        return mapper.readValue(responseString, Hotel.class);
    }

    public static String updateHotel(String token, Long hotelId, HotelInformationDto dto) throws ClientRequestException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(ApiCommon.UPDATE_ONE_HOTEL + "/" + hotelId);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("authorization", token);
        put.setEntity(new StringEntity(new Gson().toJson(dto, HotelInformationDto.class), "UTF-8"));
        HttpResponse res = client.execute(put);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200) //
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static String deleteHotel(String token, Long hotelId) throws ClientRequestException, IOException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpDelete delete = new HttpDelete(ApiCommon.DELETE_ONE_HOTEL + "/" + hotelId);
        delete.setHeader("Content-Type", "application/json");
        delete.setHeader("authorization", token);
        HttpResponse res = client.execute(delete);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200) //
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static List<Reservation> getReservations(String token) throws IOException, ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_RESERVATIONS);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("authorization", token);
        HttpResponse res = client.execute(get);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200) //
            throw new ClientRequestException(responseString);
        return mapper.readValue(responseString,  new TypeReference<List<Reservation>>(){});
    }

    public static Reservation getOneReservation(String token, Long reservationId) throws IOException, ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_ONE_RESERVATION + "/" + reservationId);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("authorization", token);
        HttpResponse res = client.execute(get);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200) //
            throw new ClientRequestException(responseString);
        return mapper.readValue(responseString, Reservation.class);
    }

    public static String deleteReservation(String token, Long reservationId) throws IOException, ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpDelete delete = new HttpDelete(ApiCommon.DELETE_RESERVATION + "/" + reservationId);
        delete.setHeader("Content-Type", "application/json");
        delete.setHeader("authorization", token);
        HttpResponse res = client.execute(delete);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200) //
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static List<Notification> getAllNotifications(String token, @Nullable String notificationType) throws IOException, ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_ALL_NOTIFICATIONS);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("authorization", token);
        if(notificationType != null)
            get.setHeader("notification-type", notificationType);
        HttpResponse res = client.execute(get);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200) //
            throw new ClientRequestException(responseString);
        return mapper.readValue(responseString,  new TypeReference<List<Notification>>(){});
    }

    public static Notification getOneNotification(String token, Long notificationId) throws IOException, ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_ONE_NOTIFICATION + "/" + notificationId);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("authorization", token);
        HttpResponse res = client.execute(get);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200) //
            throw new ClientRequestException(responseString);
        return mapper.readValue(responseString,  Notification.class);
    }

    public static String deleteOneNotification(String token, Long notificationId) throws IOException, ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpDelete delete = new HttpDelete(ApiCommon.DELETE_NOTIFICATION + "/" + notificationId);
        delete.setHeader("Content-Type", "application/json");
        delete.setHeader("authorization", token);
        HttpResponse res = client.execute(delete);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200) //
            throw new ClientRequestException(responseString);
        return responseString;
    }


}
