package br.com.loginauth.dto;

import br.com.loginauth.domain.enums.EvaluationType;

import java.time.LocalDateTime;

public record CreateGradeDTO(
        String studentCpf,
        Long disciplineId,
        Double evaluation,
        EvaluationType evaluationType,
        LocalDateTime evaluationDate
) {}
