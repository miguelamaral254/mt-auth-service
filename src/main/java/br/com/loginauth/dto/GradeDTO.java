package br.com.loginauth.dto;

import br.com.loginauth.domain.enums.EvaluationType;

import java.time.LocalDateTime;

public record GradeDTO(
        StudentDTO student,
        DisciplineDTO discipline,
        double grade,
        EvaluationType evaluationType,
        String evaluationDescription,
        LocalDateTime evaluationDate
) {}