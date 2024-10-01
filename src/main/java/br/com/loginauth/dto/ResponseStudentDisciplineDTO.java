package br.com.loginauth.dto;

// DTO para retornar informações de StudentDiscipline
public record ResponseStudentDisciplineDTO(
        Long studentDisciplineId, // ID do relacionamento entre estudante e disciplina
        String studentCpf,        // CPF do estudante
        Long disciplineId         // ID da disciplina associada
) {}
