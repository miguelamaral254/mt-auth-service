package br.com.loginauth.dto;

public record DisciplineWithClassDTO(
        Long disciplineId,
        String disciplineName,
        int workload,
        String disciplineDescription,
        SchoolClassDTO schoolClass
) {}
