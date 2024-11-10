package br.com.loginauth.controllers;

import br.com.loginauth.dto.NotificationDTO;
import br.com.loginauth.dto.SendNotificationRequestDTO;
import br.com.loginauth.dto.NotificationUpdateRequestDTO;
import br.com.loginauth.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotificationToRole(@RequestBody SendNotificationRequestDTO request) {
        notificationService.sendNotificationToRole(request.role(), request.header(), request.message());
        return ResponseEntity.ok("Notificação enviada para o papel: " + request.role());
    }

    @GetMapping("/user")
    public ResponseEntity<List<NotificationDTO>> getNotificationsForUser(@RequestParam String cpf) {
        List<NotificationDTO> notifications = notificationService.getNotificationsForUser(cpf);
        return notifications.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(notifications);
    }

    @PostMapping("/update")
    public ResponseEntity<NotificationDTO> updateNotificationReadStatus(@RequestBody NotificationUpdateRequestDTO request) {
        NotificationDTO updatedNotification = notificationService.updateNotificationReadStatus(request);
        return ResponseEntity.ok(updatedNotification);
    }
}
