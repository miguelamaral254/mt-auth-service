package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Lesson;
import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Role;
import br.com.loginauth.dto.*;
import br.com.loginauth.exceptions.ProfessorNotFoundException;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.repositories.LessonRepository;
import br.com.loginauth.repositories.StudentRepository;
import br.com.loginauth.repositories.UserRepository;
import br.com.loginauth.exceptions.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;

    public void registerStudent(StudentDTO body) {
        Optional<User> user = repository.findByCpf(body.cpf());

        if (user.isEmpty()) {
            Student newStudent = new Student();
            newStudent.setCpf(body.cpf());
            newStudent.setPassword(passwordEncoder.encode(body.password()));
            newStudent.setName(body.name());
            newStudent.setEmail(body.email());
            newStudent.setRole(Role.STUDENT);
            newStudent.setActive(body.active());
            newStudent.setCreateDate(LocalDateTime.now());
            newStudent.setBirthDate(Date.valueOf(body.birthDate()));
            newStudent.setAddress(body.address());
            newStudent.setPhone(body.phone());
            newStudent.setRegistration(body.registration());

            repository.save(newStudent);
        } else {
            throw new UserAlreadyExistsException("User already exists");
        }
    }

    public Optional<User> findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
    public void updateStudent(String cpf, StudentDTO body) {
        Optional<User> existingUser = repository.findByCpf(cpf);
        if (existingUser.isPresent() && existingUser.get() instanceof Student) {
            Student student = (Student) existingUser.get();
            student.setName(body.name());
            student.setEmail(body.email());
            student.setActive(body.active());
            student.setBirthDate(Date.valueOf(body.birthDate()));
            student.setAddress(body.address());
            student.setPhone(body.phone());
            student.setRegistration(body.registration());
            repository.save(student);
        } else {
            throw new StudentNotFoundException("Student not found");
        }
    }
    public List<LessonDTO> getLessonsByStudentCpf(String cpf) {
        // Verifica se o aluno existe
        Optional<User> studentOpt = studentRepository.findByCpf(cpf);
        if (studentOpt.isEmpty()) {
            throw new StudentNotFoundException("Student not found with CPF " + cpf);
        }

        // Obtém o aluno (User)
        User student = studentOpt.get();

        // Filtra as lições associadas ao aluno pelo CPF
        List<Lesson> lessons = lessonRepository.findAll().stream()
                .filter(lesson -> lesson.getSchoolClass().getStudents().stream()
                        .anyMatch(s -> s.getCpf().equals(cpf)))
                .collect(Collectors.toList());

        // Mapeia as lições para o DTO correspondente
        return lessons.stream()
                .map(lesson -> new LessonDTO(
                        lesson.getId(),
                        lesson.getName(), // Presumindo que Lesson possui um método getName()
                        new SchoolClassDTO(
                                lesson.getSchoolClass().getId(),
                                lesson.getSchoolClass().getLetter(),
                                lesson.getSchoolClass().getShift(),
                                lesson.getSchoolClass().getCode(),
                                lesson.getSchoolClass().getTechnicalCourse(),
                                lesson.getSchoolClass().getYear(),
                                lesson.getSchoolClass().getDate() // Se aplicável
                        ),
                        new DisciplineDTO(
                                lesson.getDiscipline().getId(),
                                lesson.getDiscipline().getName(),
                                lesson.getDiscipline().getWorkload(), // Se aplicável
                                lesson.getDiscipline().getDescription() // Se aplicável
                        ),
                        new ProfessorResponseDTO(
                                student.getCpf(),
                                student.getName()
                        ),
                        lesson.getWeekDay(), // Presumindo que Lesson possui um método getWeekDay()
                        lesson.getStartTime(), // Presumindo que Lesson possui um método getStartTime()
                        lesson.getEndTime(), // Presumindo que Lesson possui um método getEndTime()
                        lesson.getRoom() // Presumindo que Lesson possui um método getRoom()
                ))
                .distinct()
                .collect(Collectors.toList());
    }

}
