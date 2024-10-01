package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.Grade;
import br.com.loginauth.dto.CreateGradeDTO;
import br.com.loginauth.services.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    // Endpoint para criar uma nova grade
    @PostMapping
    public ResponseEntity<Grade> createGrade(@RequestBody CreateGradeDTO createGradeDTO) {
        try {
            Grade grade = gradeService.createGrade(createGradeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(grade);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Endpoint para obter todas as grades
    @GetMapping
    public ResponseEntity<List<Grade>> getAllGrades() {
        List<Grade> grades = gradeService.getAllGrades();
        return ResponseEntity.ok(grades);
    }

    // Endpoint para obter as grades por CPF do estudante
    @GetMapping("/student/{cpf}")
    public ResponseEntity<List<Grade>> getGradesByStudentCpf(@PathVariable String cpf) {
        List<Grade> grades = gradeService.getGradesByStudentCpf(cpf);
        if (grades.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(grades);
    }
    @GetMapping("/student/{cpf}/discipline/{disciplineId}")
    public ResponseEntity<List<Grade>> getGradesByStudentCpfAndDiscipline(
            @PathVariable String cpf,
            @PathVariable Long disciplineId) {
        List<Grade> grades = gradeService.getGradesByStudentCpfAndDiscipline(cpf, disciplineId);
        if (grades.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(grades);
    }
}
