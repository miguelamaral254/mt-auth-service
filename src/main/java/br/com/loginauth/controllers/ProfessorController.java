package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.Professor;
import br.com.loginauth.dto.DisciplineWithClassDTO;
import br.com.loginauth.dto.LessonDTO;
import br.com.loginauth.dto.ProfessorDTO;
import br.com.loginauth.dto.ResponseDTO;
import br.com.loginauth.exceptions.ProfessorNotFoundException;
import br.com.loginauth.services.LessonService;
import br.com.loginauth.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;
    private final LessonService lessonService;

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
    @GetMapping
    public ResponseEntity<List<Professor>> findAllProfessors() {
        List<Professor> professors = professorService.findAllProfessors();
        return ResponseEntity.ok(professors);
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
    @GetMapping("/{cpf}/disciplines")
    public ResponseEntity<List<DisciplineWithClassDTO>> getDisciplinesByProfessorCpf(@PathVariable String cpf) {
        List<DisciplineWithClassDTO> disciplines = professorService.getDisciplinesByProfessorCpf(cpf);
        return ResponseEntity.ok(disciplines);
    }
    @GetMapping("/professor/{cpf}")
    public ResponseEntity<List<LessonDTO>> getLessonsByProfessorCpf(@PathVariable String cpf) {
        try {
            List<LessonDTO> lessons =  professorService.getLessonsByProfessorCpf(cpf);
            return ResponseEntity.ok(lessons);
        } catch (ProfessorNotFoundException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o professor não for encontrado
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Retorna 500 para outros erros
        }
    }
}
