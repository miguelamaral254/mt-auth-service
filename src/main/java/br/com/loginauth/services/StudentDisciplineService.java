package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Discipline;
import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.domain.entities.StudentDiscipline;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.dto.StudentDisciplineCreateDTO;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.repositories.DisciplineRepository;
import br.com.loginauth.repositories.StudentDisciplineRepository;
import br.com.loginauth.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentDisciplineService {

    private final StudentRepository studentRepository;
    private final DisciplineRepository disciplineRepository;
    private final StudentDisciplineRepository studentDisciplineRepository;

    @Autowired
    public StudentDisciplineService(StudentRepository studentRepository, DisciplineRepository disciplineRepository, StudentDisciplineRepository studentDisciplineRepository) {
        this.studentRepository = studentRepository;
        this.disciplineRepository = disciplineRepository;
        this.studentDisciplineRepository = studentDisciplineRepository;
    }

    // Método para criar um relacionamento entre estudante e disciplina usando o DTO simplificado
    public StudentDiscipline createStudentDiscipline(StudentDisciplineCreateDTO dto) {
        Optional<User> studentOpt = studentRepository.findByCpf(dto.studentCpf());
        Optional<Discipline> disciplineOpt = disciplineRepository.findById(dto.disciplineId());

        if (studentOpt.isEmpty()) {
            throw new StudentNotFoundException("Student not found with CPF: " + dto.studentCpf());
        }

        if (disciplineOpt.isEmpty()) {
            throw new RuntimeException("Discipline not found with ID: " + dto.disciplineId());
        }

        Student student = (Student) studentOpt.get();
        Discipline discipline = disciplineOpt.get();

        StudentDiscipline studentDiscipline = new StudentDiscipline();
        studentDiscipline.setStudent(student);
        studentDiscipline.setDiscipline(discipline);

        return studentDisciplineRepository.save(studentDiscipline);
    }
}
