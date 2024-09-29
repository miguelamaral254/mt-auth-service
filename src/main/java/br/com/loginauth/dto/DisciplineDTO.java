package br.com.loginauth.dto;



public record DisciplineDTO(
        Long id,
        String name,
        int workload,
        String description
) {}
