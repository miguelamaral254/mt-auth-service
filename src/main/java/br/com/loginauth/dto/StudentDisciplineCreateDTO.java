package br.com.loginauth.dto;

// DTO para criar um relacionamento StudentDiscipline
public record StudentDisciplineCreateDTO(
        String studentCpf,
        Long disciplineId
) {}
