package br.com.loginauth.dto;

import java.time.LocalDateTime;

public record LessonDTO(
        Long id,
        SchoolClassDTO schoolClass,
        DisciplineDTO discipline,
        ProfessorDTO professor,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String room
) {}
