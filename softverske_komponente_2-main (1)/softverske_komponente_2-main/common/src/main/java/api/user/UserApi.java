package api.user;
import api.ApiCommon;
import com.fasterxml.jackson.core.type.TypeReference;
import dto.entities.ReservationDto;
import dto.entities.ReviewDto;
import exceptions.ClientRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import dto.user.ManagerInformationDto;
import dto.user.UserInformationDto;
import dto.user.UserLoginDto;
import models.Hotel;
import models.Notification;
import models.Reservation;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.lang.Nullable;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("all")
public class UserApi {

    public static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    public static String register(UserInformationDto userInfo) throws IOException, ClientRequestException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(ApiCommon.USER_REGISTER);
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(new Gson().toJson(userInfo, UserInformationDto.class)));
        HttpResponse res = client.execute(post);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");

        if(res.getStatusLine().getStatusCode() != 201) //
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static String register(ManagerInformationDto managerInfo) throws IOException, ClientRequestException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(ApiCommon.MANAGER_REGISTER);
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(new Gson().toJson(managerInfo, ManagerInformationDto.class)));
        HttpResponse res = client.execute(post);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");

        if(res.getStatusLine().getStatusCode() != 201) //
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static String login(UserLoginDto loginInfo) throws IOException, ClientRequestException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(ApiCommon.LOGIN);
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(new Gson().toJson(loginInfo, UserLoginDto.class)));
        HttpResponse res = client.execute(post);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static UserInformationDto fetchSelfInfoUser(Long id, String token) throws IOException, ClientRequestException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_SELF_INFO + "/" + id);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("authorization", token);
        HttpResponse res = client.execute(get);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return mapper.readValue(responseString, UserInformationDto.class);
    }

    public static ManagerInformationDto fetchSelfInfoManager(Long id, String token) throws IOException, ClientRequestException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_SELF_INFO + "/" + id);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("authorization", token);
        HttpResponse res = client.execute(get);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return mapper.readValue(responseString, ManagerInformationDto.class);
    }

    public static String updateSelfInfoUser(Long id, String token, UserInformationDto userInfo) throws IOException, ClientRequestException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(ApiCommon.UPDATE_SELF_INFO + "/" + id);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("authorization", token);
        put.setEntity(new StringEntity(new Gson().toJson(userInfo, UserInformationDto.class)));
        HttpResponse res = client.execute(put);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static String updateSelfInfoManager(Long id, String token, ManagerInformationDto userInfo) throws IOException, ClientRequestException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(ApiCommon.UPDATE_SELF_INFO + "/" + id);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("authorization", token);
        put.setEntity(new StringEntity(new Gson().toJson(userInfo, ManagerInformationDto.class)));
        HttpResponse res = client.execute(put);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static String reviewHotel(String token, Long hotelId, ReviewDto review) throws IOException, ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(ApiCommon.REVIEW_HOTEL + "/" + hotelId);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("authorization", token);
        put.setEntity(new StringEntity(new Gson().toJson(review, ReviewDto.class)));
        HttpResponse res = client.execute(put);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static String deleteReview(String token, Long hotelId) throws IOException, ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpDelete delete = new HttpDelete(ApiCommon.DELETE_REVIEW + "/" + hotelId);
        delete.setHeader("Content-Type", "application/json");
        delete.setHeader("authorization", token);
        HttpResponse res = client.execute(delete);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static String editReview(String token, Long hotelId, ReviewDto reviewDto) throws IOException, ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(ApiCommon.EDIT_REVIEW + "/" + hotelId);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("authorization", token);
        put.setEntity(new StringEntity(new Gson().toJson(reviewDto, ReviewDto.class)));
        HttpResponse res = client.execute(put);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static String makeReservation(String token, ReservationDto reservation) throws IOException, ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(ApiCommon.MAKE_RESERVATION);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("authorization", token);
        put.setEntity(new StringEntity(new Gson().toJson(reservation, ReservationDto.class)));
        HttpResponse res = client.execute(put);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static String cancelReservation(String token, Long reservationId) throws IOException, ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(ApiCommon.CANCEL_RESERVATION + "/" + reservationId);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("authorization", token);
        HttpResponse res = client.execute(put);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static List<Reservation> getCurrentUserReservations(String token) throws IOException, ClientRequestException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_MY_RESERVATIONS);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("authorization", token);
        HttpResponse res = client.execute(get);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200) //
            throw new ClientRequestException(responseString);
        return mapper.readValue(responseString,  new TypeReference<List<Reservation>>(){});
    }

    public static List<Notification> getMyNotifications(String token, @Nullable String notificationType) throws ClientRequestException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_MY_NOTIFICATIONS);
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

    public static String changePassword(String token, Long userId, String password) throws ClientRequestException, IOException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(ApiCommon.CHANGE_PASSWORD + "/" + userId);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("authorization", token);
        put.setHeader("new-password", password);
        HttpResponse res = client.execute(put);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static List<Hotel> getHotels(String token) throws ClientRequestException, IOException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_HOTELS);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("authorization", token);
        HttpResponse res = client.execute(get);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200) //
            throw new ClientRequestException(responseString);
        return mapper.readValue(responseString,  new TypeReference<List<Hotel>>(){});
    }

}
