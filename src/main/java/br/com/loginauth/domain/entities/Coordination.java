package br.com.loginauth.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
@Entity
@Table(name = "coordinations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coordination extends User {
    private Date birthDate;
    private String address;
    private String phone;

    @Column(unique = true, nullable = false)
    private String registration;
}
