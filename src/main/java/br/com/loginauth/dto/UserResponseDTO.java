package br.com.loginauth.dto;

public record UserResponseDTO(
        String name,
        String cpf,
        boolean active,
        String registration
) {
}
