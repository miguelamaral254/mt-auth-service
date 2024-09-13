package br.com.loginauth.dto;

import java.time.LocalDate;

public record CoordinationDTO(
        String cpf,
        String password,
        String name,
        String email,
        Boolean active,
        LocalDate birthDate,
        String address,
        String phone,
        String registration
) {}
