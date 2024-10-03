package br.com.loginauth.dto;

import br.com.loginauth.domain.enums.Schedule;
import br.com.loginauth.domain.enums.Week;


public record LessonDTO(
        Long id,
        String name,
        SchoolClassDTO schoolClass,
        DisciplineDTO discipline,
        ProfessorDTO professor,
        Week weekDay,
        Schedule startTime,
        Schedule endTime,
        String room
) {}
