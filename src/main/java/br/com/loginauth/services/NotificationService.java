package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Notification;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Role;
import br.com.loginauth.dto.NotificationDTO;
import br.com.loginauth.repositories.NotificationRepository;
import br.com.loginauth.repositories.UserRepository;
import br.com.loginauth.exceptions.UserNotFoundException;
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

    // Método para enviar notificação para todos os usuários com o papel especificado
    public void sendNotificationToRole(Role role, String message) {
        List<User> usersWithRole = userRepository.findByRole(role);

        for (User user : usersWithRole) {
            Notification notification = new Notification();
            notification.setMessage(message);
            notification.setTimestamp(LocalDateTime.now());
            notification.setUser(user);
            notificationRepository.save(notification);
        }
    }

    // Método para buscar as notificações de um usuário pelo CPF
    public List<NotificationDTO> getNotificationsForUser(String cpf) {
        User user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException("User not found with CPF " + cpf));

        return notificationRepository.findByUser(user).stream()
                .map(notification -> new NotificationDTO(
                        notification.getId(),
                        notification.getMessage(),
                        notification.getTimestamp()
                ))
                .collect(Collectors.toList());
    }
}
