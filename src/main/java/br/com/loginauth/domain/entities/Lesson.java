package br.com.loginauth.domain.entities;

import br.com.loginauth.domain.enums.Schedule;
import br.com.loginauth.domain.enums.Week;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "school_class_id", nullable = false)
    private SchoolClass schoolClass;

    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;

    @ManyToOne
    @JoinColumn(name = "professor_cpf", nullable = false)
    private Professor professor;

    @Enumerated(EnumType.STRING)
    @Column(name="week_day", nullable = false)
    private Week weekDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "start_time", nullable = false)
    private Schedule startTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "end_time", nullable = false)
    private Schedule endTime;

    @Column(nullable = false)
    private String room;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
