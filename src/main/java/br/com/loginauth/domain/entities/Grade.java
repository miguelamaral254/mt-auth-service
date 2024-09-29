package br.com.loginauth.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_discipline_id")
    @JsonManagedReference // Evita a serialização recursiva
    private StudentDiscipline studentDiscipline;

    private Double av1;
    private Double av2;
    private Double av3;
    private Double av4;
    private Double finalGrade;
    private LocalDateTime evaluationDate;
}
