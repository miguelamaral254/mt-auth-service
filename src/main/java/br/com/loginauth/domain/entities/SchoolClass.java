package br.com.loginauth.domain.entities;

import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.domain.enums.Letter;
import br.com.loginauth.domain.enums.Shift;
import br.com.loginauth.domain.enums.TechnicalCourse;
import br.com.loginauth.domain.enums.Year;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "school_class")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "letter", nullable = false)
    private Letter letter;
    @Column(name = "shift", nullable = false)
    private Shift shift;
    @Column(nullable = false, unique = true)
    private String code;
    @Column(name = "technical_course", nullable = false)
    private TechnicalCourse technicalCourse;
    @Column(name = "year", nullable = false)
    private Year year;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @ManyToMany
    @JoinTable(
            name = "class_student",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "student_cpf")
    )
    private Set<Student> students = new HashSet<>();
}
