package br.com.loginauth.controllers;

import br.com.loginauth.dto.ParentDTO;
import br.com.loginauth.dto.ResponseDTO;
import br.com.loginauth.services.ParentService;
import br.com.loginauth.exceptions.ParentNotFoundException;
import br.com.loginauth.exceptions.StudentNotFoundException;
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

    @PutMapping("/update/{cpf}")
    public ResponseEntity<ResponseDTO> updateParent(@PathVariable String cpf, @RequestBody ParentDTO body) {
        try {
            parentService.updateParent(cpf, body);
            return ResponseEntity.ok(new ResponseDTO("Parent updated successfully", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), null));
        }
    }

    @PostMapping("/{parentCpf}/add-student/{studentCpf}")
    public ResponseEntity<ResponseDTO> addStudentToParent(
            @PathVariable String parentCpf,
            @PathVariable String studentCpf) {
        try {
            parentService.addStudentToParent(parentCpf, studentCpf);
            return ResponseEntity.ok(new ResponseDTO("Student added to parent successfully", null));
        } catch (ParentNotFoundException | StudentNotFoundException e) {
            return ResponseEntity.status(404).body(new ResponseDTO(e.getMessage(), null));
        }
    }
}