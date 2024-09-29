package br.com.loginauth.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "student_discipline")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDiscipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_cpf", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;

    @OneToMany(mappedBy = "studentDiscipline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grade> grades;

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
        for (Grade grade : grades) {
            grade.setStudentDiscipline(this); // vincula cada nota à disciplina do aluno
        }
    }
}
