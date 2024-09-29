package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.Discipline;
import br.com.loginauth.dto.DisciplineDTO;
import br.com.loginauth.services.DisciplineService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplines")
@RequiredArgsConstructor
public class DisciplineController {

    private final DisciplineService disciplineService;

    @GetMapping
    public ResponseEntity<List<Discipline>> getAllDisciplines() {
        return ResponseEntity.ok(disciplineService.getAllDisciplines());
    }

    @PostMapping
    public ResponseEntity<Discipline> createDiscipline(@RequestBody Discipline discipline) {
        return ResponseEntity.ok(disciplineService.createDiscipline(discipline));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Discipline> getDisciplineById(@PathVariable Long id) {
        Discipline discipline = disciplineService.getDisciplineById(id);
        return ResponseEntity.ok(discipline);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Discipline> updateDiscipline(@PathVariable Long id, @RequestBody Discipline updatedDiscipline) {
        return ResponseEntity.ok(disciplineService.updateDiscipline(id, updatedDiscipline));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscipline(@PathVariable Long id) {
        disciplineService.deleteDiscipline(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{cpf}")
    public List<DisciplineDTO> getDisciplinesByStudentCpf(@PathVariable String cpf) {
        return disciplineService.getDisciplinesByStudentCpf(cpf);
    }
}

