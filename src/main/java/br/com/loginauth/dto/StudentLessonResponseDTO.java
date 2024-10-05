package br.com.loginauth.dto;

import br.com.loginauth.domain.enums.Schedule;
import br.com.loginauth.domain.enums.Week;

public record StudentLessonResponseDTO (
        Long id,
        String name,
        SchoolClassDTO schoolClass,
        DisciplineDTO discipline,
        StudentResponseDTO studentResponseDTO,
        ProfessorResponseDTO professorResponseDTO,
        Week weekDay,
        Schedule startTime,
        Schedule endTime,
        String room
) {}
