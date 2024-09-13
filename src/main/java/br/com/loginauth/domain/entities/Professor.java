package br.com.loginauth.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@DiscriminatorValue("PROFESSOR")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Professor extends User {
    @Column(unique = true, nullable = false)
    private String registration;

    @Column(nullable = false)
    private String expertiseArea;

    private String academicTitle;
    private String phone;
}
