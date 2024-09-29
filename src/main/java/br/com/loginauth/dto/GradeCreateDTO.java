package br.com.loginauth.dto;

import java.time.LocalDateTime;

// DTO para criar uma nova Grade associada a um StudentDiscipline
public record GradeCreateDTO(
        Long studentDisciplineId, // ID do relacionamento entre estudante e disciplina
        Double av1,               // Nota da avaliação 1
        Double av2,               // Nota da avaliação 2
        Double av3,               // Nota da avaliação 3
        Double av4,               // Nota da avaliação 4
        Double finalGrade,        // Nota final
        LocalDateTime evaluationDate // Data da avaliação
) {}
