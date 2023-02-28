package dto.entities;

import enums.NotificationTypes;

public class NotificationDto {

    private String recipient;
    private String subject;
    private String text;
    private String username;
    private String hotelName;

    public NotificationDto(){

    }

    public NotificationDto(String recipient, String subject, String username, String hotelName) {
        this.recipient = recipient;
        this.subject = subject;
        this.username = username;
        this.hotelName = hotelName;
        this.text = NotificationTypes.getMail(subject, username, hotelName);
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
}
