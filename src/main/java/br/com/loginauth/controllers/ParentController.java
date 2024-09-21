package br.com.loginauth.controllers;

import br.com.loginauth.dto.ParentDTO;
import br.com.loginauth.dto.ResponseDTO;
import br.com.loginauth.services.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parent")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerParent(@RequestBody ParentDTO body) {
        parentService.registerParent(body);
        return ResponseEntity.ok(new ResponseDTO("Parent registered successfully", null));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> findByCpf(@PathVariable String cpf) {
        return parentService.findByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
