package br.com.loginauth.controllers;

import br.com.loginauth.dto.CoordinationDTO;
import br.com.loginauth.dto.ResponseDTO;
import br.com.loginauth.services.CoordinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coordination")
@RequiredArgsConstructor
public class CoordinationController {

    private final CoordinationService coordinationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerCoordination(@RequestBody CoordinationDTO body) {
        coordinationService.registerCoordination(body);
        return ResponseEntity.ok(new ResponseDTO("Coordination registered successfully", null));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> findByCpf(@PathVariable String cpf) {
        return coordinationService.findByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/update/{cpf}")
    public ResponseEntity<ResponseDTO> updateCoordination(@PathVariable String cpf, @RequestBody CoordinationDTO body) {
        try {
            coordinationService.updateCoordination(cpf, body);
            return ResponseEntity.ok(new ResponseDTO("Coordination updated successfully", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), null));
        }
    }

}
