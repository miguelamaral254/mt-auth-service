package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.User;
/*
import br.com.loginauth.dto.*;
import br.com.loginauth.exceptions.UserAlreadyExistsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
*/
import br.com.loginauth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
/*
    @PostMapping("/register/student")
    public ResponseEntity<ResponseDTO> registerStudent(@RequestBody StudentDTO body) {
        try {
            userService.registerStudent(body);
            return ResponseEntity.ok(new ResponseDTO("Student registered successfully", null));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), null));
        }
    }

    @PostMapping("/register/parent")
    public ResponseEntity<ResponseDTO> registerParent(@RequestBody ParentDTO body) {
        try {
            userService.registerParent(body);
            return ResponseEntity.ok(new ResponseDTO("Parent registered successfully", null));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), null));
        }
    }

    @PostMapping("/register/professor")
    public ResponseEntity<ResponseDTO> registerProfessor(@RequestBody ProfessorDTO body) {
        try {
            userService.registerProfessor(body);
            return ResponseEntity.ok(new ResponseDTO("Professor registered successfully", null));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), null));
        }
    }

    @PostMapping("/register/coordination")
    public ResponseEntity<ResponseDTO> registerCoordination(@RequestBody CoordinationDTO body) {
        try {
            userService.registerCoordination(body);
            return ResponseEntity.ok(new ResponseDTO("Coordination registered successfully", null));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), null));
        }
    }
*/
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{cpf}")
    public ResponseEntity<?> findByCpf(@PathVariable String cpf) {
        return userService.findByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
