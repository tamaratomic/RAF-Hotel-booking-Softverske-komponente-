package repositories;

import models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Long> {
    List<Notification> findByType(String type);
    List<Notification> findByRecipient(String recipient);
    List<Notification> findByRecipientAndType(String recipient, String type);
}

