package br.com.loginauth.dto;

import br.com.loginauth.domain.enums.EvaluationType;

public record EvaluationDTO(
        Long gradeId,
        double evaluation,
        String description,
        EvaluationType evaluationType
) {}
