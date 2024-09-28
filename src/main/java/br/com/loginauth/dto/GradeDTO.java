package br.com.loginauth.dto;



import br.com.loginauth.domain.entities.Evaluation;
import br.com.loginauth.domain.enums.Semester;

import java.util.List;

public record GradeDTO(
        Long assessmentsId,
        List<Evaluation> evaluations,
        double finalGrade,
        Semester semester
) {}
