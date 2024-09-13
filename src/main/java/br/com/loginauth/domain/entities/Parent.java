package br.com.loginauth.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@DiscriminatorValue("PARENT") // Valor para a coluna discriminadora
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Parent extends User {
    @Column(nullable = false)
    private Date birthDate;

    private String address;
    private String phone;
}
