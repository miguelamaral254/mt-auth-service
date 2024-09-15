package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.Discipline;
import br.com.loginauth.services.DisciplineService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disciplines")
@RequiredArgsConstructor
public class DisciplineController {

    private final DisciplineService disciplineService;

    @PostMapping
    public ResponseEntity<Discipline> createDiscipline(@RequestBody Discipline discipline) {
        return ResponseEntity.ok(disciplineService.createDiscipline(discipline));
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
}

