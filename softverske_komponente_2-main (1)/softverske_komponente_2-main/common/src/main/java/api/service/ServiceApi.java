package api.service;

import api.ApiCommon;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import exceptions.ClientRequestException;
import models.Reservation;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class ServiceApi {

    @SuppressWarnings("all")

    public static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    public static boolean adjustPoints(String token, Long id, String action, Long managerId, String hotelName){
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPut put = new HttpPut(ApiCommon.ADJUST_POINTS + "/" + id);
            put.setHeader("Content-Type", "application/json");
            put.setHeader("authorization", token);
            put.setHeader("action-type", action);
            put.setHeader("manager-id", managerId.toString());
            put.setHeader("hotel-name", hotelName);
            HttpResponse res = client.execute(put);
            String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
            return res.getStatusLine().getStatusCode() == 200;
        }catch (Exception e) {
            return false;
        }
    }

    public static List<Reservation> getReservations(String key) throws IOException, ClientRequestException {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(ApiCommon.GET_SERVICE_RESERVATIONS);
            get.setHeader("Content-Type", "application/json");
            get.setHeader("key", key);
            HttpResponse res = client.execute(get);
            String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
            if(res.getStatusLine().getStatusCode() !=200)
                throw new ClientRequestException(responseString);
            return mapper.readValue(responseString,  new TypeReference<List<Reservation>>(){});

    }

    public static String getEmail(String key, Long id) throws IOException, ClientRequestException{
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(ApiCommon.GET_USER_EMAIL + "/" + id);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("key", key);
        HttpResponse res = client.execute(get);
        String responseString = EntityUtils.toString(res.getEntity(), "UTF-8");
        if(res.getStatusLine().getStatusCode() !=200)
            throw new ClientRequestException(responseString);
        return responseString;
    }

}
