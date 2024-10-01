package br.com.loginauth.dto;

import java.time.LocalDateTime;

public record GradeCreateDTO(
        Long studentDisciplineId,
        Double av1,
        Double av2,
        Double av3,
        Double av4,
        Double finalGrade,
        LocalDateTime evaluationDate
) {}
