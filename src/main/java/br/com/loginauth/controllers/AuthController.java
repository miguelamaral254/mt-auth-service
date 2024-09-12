package br.com.loginauth.controllers;
import br.com.loginauth.domain.user.User;
import br.com.loginauth.dto.LoginRequestDTO;
import br.com.loginauth.dto.RegisterRequestDTO;
import br.com.loginauth.dto.ResponseDTO;
import br.com.loginauth.infra.security.TokenService;
import br.com.loginauth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        try {
            User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
            if (passwordEncoder.matches(body.password(), user.getPassword())) {
                String token = this.tokenService.generateToken(user);
                System.out.println("foi");
                return ResponseEntity.ok(new ResponseDTO(user.getName(), token));

            }
            return ResponseEntity.status(401).body("Invalid password");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }



    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}