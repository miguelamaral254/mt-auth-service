package br.com.loginauth.controllers;

import br.com.loginauth.dtos.NotificationDTO;
import br.com.loginauth.services.NotificationService;
import br.com.loginauth.domain.entities.User; 
import br.com.loginauth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository; 

    @PostMapping("/send-to-role")
    public ResponseEntity<String> sendNotificationToRole(
        @RequestParam String role, 
        @RequestParam String message
    ) {
        notificationService.sendNotificationToRole(role, message);
        return ResponseEntity.ok("Notificação enviada para: " + role);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsForUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<NotificationDTO> notifications = notificationService.getNotificationsForUser(user);
        return ResponseEntity.ok(notifications);
    }
}
