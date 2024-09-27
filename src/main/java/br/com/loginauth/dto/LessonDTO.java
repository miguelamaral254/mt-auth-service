package br.com.loginauth.dto;

import java.time.LocalDateTime;

public record LessonDTO(
        Long id,
        String name,
        SchoolClassDTO schoolClass,
        DisciplineDTO discipline,
        ProfessorDTO professor,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String room
) {}
