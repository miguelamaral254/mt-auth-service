package br.com.loginauth.dto;

import br.com.loginauth.domain.enums.EvaluationType;

import java.time.LocalDateTime;

// DTO para responder com as informações da Grade
public record GradeResponseDTO(
        Long gradeId,                   // ID da Grade
        String studentCpf,              // CPF do estudante
        Long disciplineId,              // ID da disciplina
        String disciplineName,          // Nome da disciplina
        Double evaluation,              // Nota da avaliação
        EvaluationType evaluationType,  // Tipo da avaliação (AV1, AV2, etc)
        LocalDateTime evaluationDate    // Data da avaliação
) {}
