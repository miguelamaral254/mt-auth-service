package br.com.loginauth.services;

import br.com.loginauth.dtos.NotificationDTO;
import br.com.loginauth.domain.entities.Notification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationDTO> getNotificationsForUser(User user) {
        List<Notification> notifications = notificationRepository.findByUser(user);
        return notifications.stream()
                .map(notification -> new NotificationDTO(notification.getId(), notification.getMessage(), notification.getTimestamp()))
                .collect(Collectors.toList());
    }
}
