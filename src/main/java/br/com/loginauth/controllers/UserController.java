package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.Parent;
import br.com.loginauth.domain.entities.Professor;
import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Role;
import br.com.loginauth.dto.RegisterRequestDTO;
import br.com.loginauth.dto.ResponseDTO;
import br.com.loginauth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Qualifier("parentRepository")
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        Optional<User> user = this.repository.findByCpf(body.cpf());

        if (user.isEmpty()) {
            User newUser = createUserFromDTO(body);

            this.repository.save(newUser);

            return ResponseEntity.ok(new ResponseDTO("User registered successfully", null));
        }
        return ResponseEntity.badRequest().body(new ResponseDTO("User already exists", null));
    }

    private User createUserFromDTO(RegisterRequestDTO body) {
        User newUser = new User();
        newUser.setCpf(body.cpf());
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setRole(Role.valueOf(body.role().toUpperCase()));
        newUser.setActive(true);
        newUser.setCreateDate(LocalDateTime.now());

        if (body.role().equalsIgnoreCase("PARENT")) {
            newUser = new Parent();
            ((Parent) newUser).setBirthDate(body.birthDate());
            ((Parent) newUser).setAddress(body.address());
            ((Parent) newUser).setPhone(body.phone());
        } else if (body.role().equalsIgnoreCase("PROFESSOR")) {
            newUser = new Professor();
            // Set campos específicos de professor
        } else if (body.role().equalsIgnoreCase("STUDENT")) {
            newUser = new Student();
            // Set campos específicos de estudante
        }

        return newUser;
    }
}
