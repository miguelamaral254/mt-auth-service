package br.com.loginauth.services;

import br.com.loginauth.domain.entities.*;
/*
import br.com.loginauth.domain.enums.Role;

import br.com.loginauth.dto.*;
import br.com.loginauth.repositories.CoordinationRepository;
import br.com.loginauth.exceptions.UserAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.sql.Date;
import java.time.LocalDateTime;
 */
import br.com.loginauth.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
   // private final PasswordEncoder passwordEncoder;
   // private final CoordinationRepository coordinationRepository;
   // private final UserRepository userRepository;
/*
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

    public void registerParent(ParentDTO body) {
        Optional<User> user = repository.findByCpf(body.cpf());

        if (user.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        Optional<User> student = repository.findByCpf(body.studentCpf());
        if (student.isEmpty() || !(student.get() instanceof Student)) {
            throw new IllegalArgumentException("Student does not exist");
        }

        Parent newParent = new Parent();
        newParent.setCpf(body.cpf());
        newParent.setPassword(passwordEncoder.encode(body.password()));
        newParent.setName(body.name());
        newParent.setEmail(body.email());
        newParent.setRole(Role.PARENT);
        newParent.setActive(body.active());
        newParent.setCreateDate(LocalDateTime.now());
        newParent.setBirthDate(Date.valueOf(body.birthDate()));
        newParent.setAddress(body.address());
        newParent.setPhone(body.phone());
        newParent.setStudentCPF(body.studentCpf());

        repository.save(newParent);
    }

    public void registerProfessor(ProfessorDTO body) {
        Optional<User> user = repository.findByCpf(body.cpf());

        if (user.isEmpty()) {
            Professor newProfessor = new Professor();
            newProfessor.setCpf(body.cpf());
            newProfessor.setPassword(passwordEncoder.encode(body.password()));
            newProfessor.setName(body.name());
            newProfessor.setEmail(body.email());
            newProfessor.setRole(Role.PROFESSOR);
            newProfessor.setActive(body.active());
            newProfessor.setCreateDate(LocalDateTime.now());
            newProfessor.setBirthDate(Date.valueOf(String.valueOf(body.birthDate())));
            newProfessor.setAddress(body.address());
            newProfessor.setPhone(body.phone());
            newProfessor.setRegistration(body.registration());
            newProfessor.setExpertiseArea(body.expertiseArea());
            newProfessor.setAcademicTitle(body.academicTitle());

            repository.save(newProfessor);
        } else {
            throw new UserAlreadyExistsException("User already exists");
        }
    }

    public void registerCoordination(CoordinationDTO body) {
        Optional<User> user = userRepository.findByCpf(body.cpf());

        if (user.isEmpty()) {
            Coordination newCoordination = new Coordination();
            newCoordination.setCpf(body.cpf());
            newCoordination.setPassword(passwordEncoder.encode(body.password()));
            newCoordination.setName(body.name());
            newCoordination.setEmail(body.email());
            newCoordination.setRole(Role.ADMIN);
            newCoordination.setActive(body.active());
            newCoordination.setCreateDate(LocalDateTime.now());
            newCoordination.setBirthDate(Date.valueOf(body.birthDate()));
            newCoordination.setAddress(body.address());
            newCoordination.setPhone(body.phone());
            newCoordination.setRegistration(body.registration());

            coordinationRepository.save(newCoordination);
        } else {
            throw new UserAlreadyExistsException("User already exists");
        }
    }
*/
    public List<User> getAllUsers() {
        return repository.findAll();
    }
    public Optional<User> findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

}
