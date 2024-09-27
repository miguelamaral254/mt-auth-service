package br.com.loginauth.dto;

import br.com.loginauth.domain.enums.Letter;
import br.com.loginauth.domain.enums.Shift;
import br.com.loginauth.domain.enums.TechnicalCourse;
import br.com.loginauth.domain.enums.Year;
import java.util.Date;

public record SchoolClassDTO(
        Long id,
        Letter letter,
        Shift shift,
        String code,
        TechnicalCourse technicalCourse,
        Year year,
        Date date
) {
}
