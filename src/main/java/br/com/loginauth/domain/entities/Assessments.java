package br.com.loginauth.domain.entities;

import br.com.loginauth.domain.enums.Situation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "assessments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assessments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_cpf", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;

    @OneToMany(mappedBy = "assessments", cascade = CascadeType.ALL)
    private List<Grade> grades;

    private double finalGrade;

    @Enumerated(EnumType.STRING)
    private Situation situation;

    @Column(nullable = false)
    private LocalDateTime evaluationDate = LocalDateTime.now();

    // Método para calcular a média das notas
    public double calculateFinalGrade() {
        if (grades == null || grades.isEmpty()) {
            return 0;
        }
        double total = grades.stream().mapToDouble(Grade::getFinalGrade).sum();
        return total / grades.size();
    }
}
