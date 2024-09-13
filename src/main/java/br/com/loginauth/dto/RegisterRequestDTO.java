package br.com.loginauth.dto;

import java.sql.Date;

public record RegisterRequestDTO(
        String name,
        String cpf,
        String password,
        String role,
        boolean active,
        String email,
        Date birthDate,
        String address,
        String phone
) {}
