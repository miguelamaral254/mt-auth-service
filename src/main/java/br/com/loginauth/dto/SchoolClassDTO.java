package br.com.loginauth.dto;

import java.util.Date;

public record SchoolClassDTO(
        Long id,
        String name,
        String code,
        Date date
) {
}
