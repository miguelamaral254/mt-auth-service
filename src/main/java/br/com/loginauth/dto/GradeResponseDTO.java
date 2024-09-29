package br.com.loginauth.dto;

import java.time.LocalDateTime;

// DTO para responder com as informações da Grade
public record GradeResponseDTO(
        Long gradeId,                   // ID da Grade
        Double av1,                     // Nota da avaliação 1
        Double av2,                     // Nota da avaliação 2
        Double av3,                     // Nota da avaliação 3
        Double av4,                     // Nota da avaliação 4
        Double finalGrade,              // Nota final
        LocalDateTime evaluationDate,   // Data da avaliação
        Long disciplineId,              // ID da disciplina
        String disciplineName,          // Nome da disciplina
        int disciplineWorkload          // Carga horária da disciplina
) {}
