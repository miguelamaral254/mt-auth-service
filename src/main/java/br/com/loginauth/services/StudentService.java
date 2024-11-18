package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Lesson;
import br.com.loginauth.domain.entities.SchoolClass;
import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Role;
import br.com.loginauth.dto.*;
import br.com.loginauth.exceptions.ProfessorNotFoundException;
import br.com.loginauth.exceptions.SchoolClassNotFoundException;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.repositories.LessonRepository;
import br.com.loginauth.repositories.SchoolClassRepository;
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
    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;

    public List<User> findAllStudents() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.STUDENT)
                .map(user -> {
                    User student = new User();
                    student.setCpf(user.getCpf());
                    student.setName(user.getName());
                    student.setEmail(user.getEmail());
                    student.setPassword(user.getPassword());
                    student.setActive(user.isActive());
                    student.setCreateDate(user.getCreateDate());
                    return student;
                })
                .collect(Collectors.toList());
    }

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
    public List<StudentLessonResponseDTO> getLessonsByStudentCpf(String cpf) {

        Optional<User> studentOpt = studentRepository.findByCpf(cpf);
        if (studentOpt.isEmpty()) {
            throw new StudentNotFoundException("Student not found with CPF " + cpf);
        }
        User student = studentOpt.get();

        List<Lesson> lessons = lessonRepository.findAll().stream()
                .filter(lesson -> lesson.getSchoolClass().getStudents().stream()
                        .anyMatch(s -> s.getCpf().equals(cpf)))
                .collect(Collectors.toList());


        return lessons.stream()
                .map(lesson -> new StudentLessonResponseDTO(
                        lesson.getId(),
                        lesson.getName(),
                        new SchoolClassDTO(
                                lesson.getSchoolClass().getId(),
                                lesson.getSchoolClass().getLetter(),
                                lesson.getSchoolClass().getShift(),
                                lesson.getSchoolClass().getCode(),
                                lesson.getSchoolClass().getTechnicalCourse(),
                                lesson.getSchoolClass().getYear(),
                                lesson.getSchoolClass().getDate()
                        ),
                        new DisciplineDTO(
                                lesson.getDiscipline().getId(),
                                lesson.getDiscipline().getName(),
                                lesson.getDiscipline().getWorkload(),
                                lesson.getDiscipline().getDescription()
                        ),
                        new StudentResponseDTO(
                                student.getCpf(),
                                student.getName()

                        ),
                        new ProfessorResponseDTO(
                                lesson.getProfessor().getName(),
                                lesson.getProfessor().getCpf()

                        ),

                        lesson.getWeekDay(),
                        lesson.getStartTime(),
                        lesson.getEndTime(),
                        lesson.getRoom()
                ))
                .distinct()
                .collect(Collectors.toList());
    }
    public List<SchoolClassDTO> getSchoolClassByStudentCPF(String studentCpf) {
        Student student = (Student) studentRepository.findByCpf(studentCpf)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with CPF " + studentCpf));

        // Filtra todas as turmas em que o estudante está matriculado
        List<SchoolClass> schoolClasses = schoolClassRepository.findAll().stream()
                .filter(sc -> sc.getStudents().contains(student))
                .collect(Collectors.toList());

        if (schoolClasses.isEmpty()) {
            throw new SchoolClassNotFoundException("No school class found for student with CPF " + studentCpf);
        }

        // Mapeia as turmas para o formato DTO
        return schoolClasses.stream()
                .map(schoolClass -> new SchoolClassDTO(
                        schoolClass.getId(),
                        schoolClass.getLetter(),
                        schoolClass.getShift(),
                        schoolClass.getCode(),
                        schoolClass.getTechnicalCourse(),
                        schoolClass.getYear(),
                        schoolClass.getDate()
                ))
                .collect(Collectors.toList());
    }

}
