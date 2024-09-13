package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.User;
import br.com.loginauth.dto.LoginRequestDTO;
import br.com.loginauth.dto.ResponseDTO;
import br.com.loginauth.infra.security.TokenService;
import br.com.loginauth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Qualifier("parentRepository")
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
        try {
            User user = this.repository.findByCpf(body.cpf())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (passwordEncoder.matches(body.password(), user.getPassword()) && user.isActive()) {
                String token = this.tokenService.generateToken(user);
                return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
            }
            return ResponseEntity.status(401).body(new ResponseDTO("Invalid password or account is inactive", null));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ResponseDTO(e.getMessage(), null));
        }
    }
}
