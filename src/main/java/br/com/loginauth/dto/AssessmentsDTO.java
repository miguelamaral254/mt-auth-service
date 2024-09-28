package br.com.loginauth.dto;



import br.com.loginauth.domain.enums.Situation;

import java.time.LocalDateTime;

public record AssessmentsDTO(
        StudentDTO student,
        DisciplineDTO discipline,
        double finalGrade,
        LocalDateTime evaluationDate,
        Situation situation
) {}
