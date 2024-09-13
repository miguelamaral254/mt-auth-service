package br.com.loginauth.dto;

import java.time.LocalDate;

public record RegisterRequestDTO(
        String name,
        String cpf,
        String password,
        String role,
        boolean active,
        String email,
        LocalDate birthDate,  // Mude para LocalDate
        String address,
        String phone,
        String registration
) {}