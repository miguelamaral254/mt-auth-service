package br.com.loginauth.dto;

public record StudentDisciplineDTO(
         String studentCpf,
         Long disciplineId,
         Long gradeId
) {}
