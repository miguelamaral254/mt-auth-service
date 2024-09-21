package br.com.loginauth.controllers;

import br.com.loginauth.dto.ProfessorDTO;
import br.com.loginauth.dto.ResponseDTO;
import br.com.loginauth.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerProfessor(@RequestBody ProfessorDTO body) {
        professorService.registerProfessor(body);
        return ResponseEntity.ok(new ResponseDTO("Professor registered successfully", null));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> findByCpf(@PathVariable String cpf) {
        return professorService.findByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/update/{cpf}")
    public ResponseEntity<ResponseDTO> updateProfessor(@PathVariable String cpf, @RequestBody ProfessorDTO body) {
        try {
            professorService.updateProfessor(cpf, body);
            return ResponseEntity.ok(new ResponseDTO("Professor updated successfully", null));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), null));
        }
    }
}
