package br.com.loginauth.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "professors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Professor extends User {
    @Column(unique = true, nullable = false)
    private String registration;
    private Date birthDate;
    private String expertiseArea;
    private String academicTitle;
    private String phone;
    private String address;
}
