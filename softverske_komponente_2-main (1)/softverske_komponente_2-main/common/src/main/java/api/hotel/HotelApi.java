package api.hotel;

import api.ApiCommon;
import com.google.gson.Gson;
import dto.entities.RoomChangeDto;
import dto.hotels.HotelInformationDto;
import dto.hotels.HotelUpdateDto;
import exceptions.ClientRequestException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import exceptions.RequestException;
import models.Hotel;
import models.HotelRoom;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.PathVariable;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.List;
@SuppressWarnings("all")
public class HotelApi {

    public static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    public static List<Hotel> getAll(String token) throws IOException, ClientRequestException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_HOTELS);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("authorization", token);
        HttpResponse res = client.execute(get);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return mapper.readValue(responseString, new TypeReference<List<Hotel>>() {
        });
    }

    public static Hotel getManagerHotel(String token) throws ClientRequestException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_MANAGER_HOTEL);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("authorization", token);
        HttpResponse res = client.execute(get);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return mapper.readValue(responseString, Hotel.class);
    }

    public static String editManagerHotel(String token, HotelUpdateDto dto) throws ClientRequestException, IOException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(ApiCommon.EDIT_MANAGER_HOTEL);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("authorization", token);
        put.setEntity(new StringEntity(new Gson().toJson(dto, HotelUpdateDto.class), "UTF-8"));
        HttpResponse res = client.execute(put);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static String addMyHotel(String token, HotelUpdateDto dto) throws ClientRequestException, IOException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(ApiCommon.ADD_MANAGER_HOTEL);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("authorization", token);
        put.setEntity(new StringEntity(new Gson().toJson(dto, HotelUpdateDto.class), "UTF-8"));
        HttpResponse res = client.execute(put);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return responseString;
    }

    public static String editMyHotelRooms(String token, List<RoomChangeDto> roomLayout) throws ClientRequestException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(ApiCommon.EDIT_MANAGER_HOTEL_ROOMS);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("authorization", token);
        put.setEntity(new StringEntity(mapper.writeValueAsString(roomLayout), "UTF-8"));
        HttpResponse res = client.execute(put);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() != 200)
            throw new ClientRequestException(responseString);
        return responseString;
    }
}
