package br.com.loginauth.controllers;

import br.com.loginauth.dto.NotificationDTO;
import br.com.loginauth.services.NotificationService;
import br.com.loginauth.domain.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Método para enviar notificação para um papel específico
    @PostMapping("/send")
    public ResponseEntity<String> sendNotificationToRole(
            @RequestParam Role role,
            @RequestParam String message
    ) {
        notificationService.sendNotificationToRole(role, message);
        return ResponseEntity.ok("Notificação enviada para o papel: " + role);
    }

    // Método para buscar notificações de um usuário
    @GetMapping("/user")
    public ResponseEntity<List<NotificationDTO>> getNotificationsForUser(@RequestParam String cpf) {
        List<NotificationDTO> notifications = notificationService.getNotificationsForUser(cpf);
        return ResponseEntity.ok(notifications);
    }
}
