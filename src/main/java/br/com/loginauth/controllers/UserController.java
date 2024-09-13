package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.Parent;
import br.com.loginauth.domain.entities.Professor;
import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Role;
import br.com.loginauth.dto.*;
import br.com.loginauth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Qualifier("parentRepository")
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register/student")
    public ResponseEntity<ResponseDTO> registerStudent(@RequestBody StudentDTO body) {
        Optional<User> user = this.repository.findByCpf(body.cpf());

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

            this.repository.save(newStudent);
            return ResponseEntity.ok(new ResponseDTO("Student registered successfully", null));
        }
        return ResponseEntity.badRequest().body(new ResponseDTO("User already exists", null));
    }

    @PostMapping("/register/parent")
    public ResponseEntity<ResponseDTO> registerParent(@RequestBody ParentDTO body) {
        Optional<User> user = this.repository.findByCpf(body.cpf());

        if (user.isEmpty()) {
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

            this.repository.save(newParent);
            return ResponseEntity.ok(new ResponseDTO("Parent registered successfully", null));
        }
        return ResponseEntity.badRequest().body(new ResponseDTO("User already exists", null));
    }

    @PostMapping("/register/professor")
    public ResponseEntity<ResponseDTO> registerProfessor(@RequestBody ProfessorDTO body) {
        Optional<User> user = this.repository.findByCpf(body.cpf());

        if (user.isEmpty()) {
            Professor newProfessor = new Professor();
            newProfessor.setCpf(body.cpf());
            newProfessor.setPassword(passwordEncoder.encode(body.password()));
            newProfessor.setName(body.name());
            newProfessor.setEmail(body.email());
            newProfessor.setRole(Role.PROFESSOR);
            newProfessor.setActive(body.active());
            newProfessor.setCreateDate(LocalDateTime.now());
            newProfessor.setBirthDate(Date.valueOf(body.birthDate()));
            newProfessor.setAddress(body.address());
            newProfessor.setPhone(body.phone());
            newProfessor.setRegistration(body.registration());
            newProfessor.setExpertiseArea(body.expertiseArea());
            newProfessor.setAcademicTitle(body.academicTitle());

            this.repository.save(newProfessor);
            return ResponseEntity.ok(new ResponseDTO("Professor registered successfully", null));
        }
        return ResponseEntity.badRequest().body(new ResponseDTO("User already exists", null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.repository.findAll();
        return ResponseEntity.ok(users);
    }
}
