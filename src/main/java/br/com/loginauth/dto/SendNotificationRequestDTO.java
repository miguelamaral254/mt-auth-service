package br.com.loginauth.dto;

import br.com.loginauth.domain.enums.Role;

public record SendNotificationRequestDTO(
        Role role,
        String header,
        String message
) {}
