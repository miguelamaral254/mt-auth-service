package br.com.loginauth.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "parents")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Parent extends User {
    private Date birthDate;
    private String address;
    private String phone;

    @OneToMany
    @JoinColumn(name = "student_cpf", referencedColumnName = "cpf")  // Associa cpf de Parent com cada Student
    private List<Student> students;
}