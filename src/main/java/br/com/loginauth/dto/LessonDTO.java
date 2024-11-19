package br.com.loginauth.dto;

import br.com.loginauth.domain.enums.Schedule;
import br.com.loginauth.domain.enums.Week;

import java.time.LocalDateTime;


public record LessonDTO(
        Long id,
        String name,
        SchoolClassDTO schoolClass,
        DisciplineDTO discipline,
        ProfessorResponseDTO professor,
        Week weekDay,
        Schedule startTime,
        Schedule endTime,
        String room,
        LocalDateTime createdAt

) {}
