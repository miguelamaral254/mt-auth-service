package br.com.loginauth.dto;

import java.time.LocalDateTime;

public record GradeDTO(
        double av1,
        double av2,
        double av3,
        double av4,
        double finalGrade,
        LocalDateTime evaluationDate,
        String studentCpf, // Incluindo o CPF do estudante diretamente
        Long disciplineId   // Incluindo o ID da disciplina diretamente
) {}
