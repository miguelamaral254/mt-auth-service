package br.com.loginauth.dto;

import java.time.LocalDateTime;

public record NotificationDTO(
        Long id,
        String message,
        LocalDateTime timestamp,
        boolean read
) {}
