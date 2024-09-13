package br.com.loginauth.dto;

import java.time.LocalDate;

public record ParentDTO(
        String name,
        String cpf,
        String password,
        boolean active,
        String email,
        LocalDate birthDate,
        String address,
        String phone,
        String studentCpf 
) {}
