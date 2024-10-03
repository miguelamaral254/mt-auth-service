package br.com.loginauth.dto;

import br.com.loginauth.domain.enums.EvaluationType;

import java.time.LocalDateTime;

// DTO para responder com as informações da Grade
public record GradeResponseDTO(
        Long gradeId,
        String studentCpf,
        Long disciplineId,
        String disciplineName,
        Double evaluation,
        EvaluationType evaluationType,
        LocalDateTime evaluationDate
) {}
