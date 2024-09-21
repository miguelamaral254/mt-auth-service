package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Role;
import br.com.loginauth.dto.StudentDTO;
import br.com.loginauth.repositories.UserRepository;
import br.com.loginauth.exceptions.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

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
            throw new IllegalArgumentException("Student not found");
        }
    }

}
