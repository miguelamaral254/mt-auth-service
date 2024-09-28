package br.com.loginauth.domain.entities;

import br.com.loginauth.domain.enums.Semester;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    @JoinColumn(name = "assessments_id", nullable = false)
    private Assessments assessments;

    @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations;

    @Enumerated(EnumType.STRING)
    private Semester semester; // Mudança de Quarter para Semester

    private double finalGrade; // Média de todas as avaliações

    // Método para calcular a média das avaliações
    public double calculateAverage() {
        if (evaluations == null || evaluations.isEmpty()) {
            return 0;
        }
        double total = evaluations.stream().mapToDouble(Evaluation::getEvaluation).sum();
        return total / evaluations.size();
    }
}
