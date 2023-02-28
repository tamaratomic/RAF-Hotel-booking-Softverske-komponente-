package rest.configuration;

import api.service.ServiceApi;
import dto.entities.NotificationDto;
import enums.NotificationTypes;
import models.Notification;
import models.Reservation;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.joda.time.DateTime;
import repositories.NotificationRepo;
import rest.listener.helper.MessageHelper;
import rest.services.EmailService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Scheduler {

    public static List<Reservation> reservationsReminded = new ArrayList<>();
    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
    private static final String key = "37fe66b4-e44b-48e7-932b-4196483786c0";

    private NotificationRepo notificationRepo;
    private EmailService emailService;

    public Scheduler(NotificationRepo notificationRepo, EmailService emailService) {
        this.notificationRepo = notificationRepo;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 900_000)
    public void sendReservationReminder(){
        System.out.println("Pakao" + DateTime.now());
        try {
            List<Reservation> reservations = ServiceApi.getReservations(key);
            DateTime start;
            DateTime end = DateTime.now();
            for(Reservation r: reservations){
                  start = formatter.parseDateTime(r.getFrom());
                  if(Days.daysBetween(end.toLocalDate(), start.toLocalDate()).getDays() <= 2
                     && !checkReminded(r)){
                      NotificationDto notificationDto = new NotificationDto(ServiceApi.getEmail(key, r.getUserId()),
                              NotificationTypes.RESERVATION_REMINDER,
                              null,
                              r.getHotelName()
                      );
                      emailService.sendSimpleMessage(notificationDto.getRecipient(), notificationDto.getSubject(), notificationDto.getText());
                      Notification notification = new Notification(notificationDto.getRecipient(), notificationDto.getSubject());
                      notificationRepo.save(notification);
                      reservationsReminded.add(r);
                  }
            }
        }catch (Exception e){
            return;
        }
    }

    private boolean checkReminded(Reservation potential){
        for(Reservation reminded: reservationsReminded){
            if(reminded.getUserId().equals(potential.getUserId()) &&
               reminded.getCreatedAt().equals(potential.getCreatedAt()) &&
               reminded.getFrom().equals(potential.getFrom()) &&
               reminded.getUntil().equals(potential.getUntil()) &&
               reminded.getHotelId().equals(potential.getHotelId()))
                return true;
        }
        return false;
    }
}
