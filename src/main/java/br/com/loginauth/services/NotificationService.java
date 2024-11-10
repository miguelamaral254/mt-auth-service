package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Notification;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Role;
import br.com.loginauth.dto.NotificationDTO;
import br.com.loginauth.dto.NotificationUpdateRequestDTO;
import br.com.loginauth.exceptions.NotificationNotFoundException;
import br.com.loginauth.exceptions.UserNotFoundException;
import br.com.loginauth.repositories.NotificationRepository;
import br.com.loginauth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public void sendNotificationToRole(Role role, String header, String message) {
        List<User> usersWithRole = userRepository.findByRole(role);
        for (User user : usersWithRole) {
            Notification notification = new Notification();

            notification.setHeader(header);
            notification.setMessage(message);
            notification.setTimestamp(LocalDateTime.now());
            notification.setUser(user);
            notification.setRead(false);
            notificationRepository.save(notification);
        }
    }

    public List<NotificationDTO> getNotificationsForUser(String cpf) {
        User user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException("User not found with CPF " + cpf));
        return notificationRepository.findByUser(user).stream()
                .map(notification -> new NotificationDTO(
                        notification.getId(),
                        notification.getHeader(),
                        notification.getMessage(),
                        notification.getTimestamp(),
                        notification.isRead()
                ))
                .collect(Collectors.toList());
    }

    public NotificationDTO updateNotificationReadStatus(NotificationUpdateRequestDTO request) {
        Notification notification = notificationRepository.findById(request.id())
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found with id " + request.id()));
        notification.setRead(request.read());
        notificationRepository.save(notification);
        return new NotificationDTO(
                notification.getId(),
                notification.getHeader(),
                notification.getMessage(),
                notification.getTimestamp(),
                request.read());
    }
}
