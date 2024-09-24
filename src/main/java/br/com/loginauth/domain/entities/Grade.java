package br.com.loginauth.domain.entities;

import br.com.loginauth.domain.enums.EvaluationType;
import br.com.loginauth.domain.enums.Quarter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "grades")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_cpf", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;

    @Column(nullable = false)
    private double grade;

    @Column(nullable = false)
    private Quarter quarter;

    @Column(nullable = false)
    private EvaluationType evaluationType; // E.g., AV1, AV2, etc.

    @Column(name = "evaluation_description", nullable = false)
    private String evaluationDescription;

    @Column(name = "evaluation_date", nullable = false)
    private LocalDateTime evaluationDate = LocalDateTime.now();
}
