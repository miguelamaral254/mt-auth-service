package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.Grade;
import br.com.loginauth.dto.CreateGradeDTO;
import br.com.loginauth.exceptions.StudentNotFoundException;
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
    public ResponseEntity<?> createGrade(@RequestBody CreateGradeDTO createGradeDTO) {
        try {
            Grade grade = gradeService.createGrade(createGradeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(grade);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar ou atualizar a nota.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Grade>> getAllGrades() {
        List<Grade> grades = gradeService.getAllGrades();
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/student/{cpf}")
    public ResponseEntity<?> getGradesByStudentCpf(@PathVariable String cpf) {
        try {
            List<Grade> grades = gradeService.getGradesByStudentCpf(cpf);
            if (grades.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(grades);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Endpoint para obter as grades por CPF do estudante e disciplina
    @GetMapping("/student/{cpf}/discipline/{disciplineId}")
    public ResponseEntity<?> getGradesByStudentCpfAndDiscipline(
            @PathVariable String cpf,
            @PathVariable Long disciplineId) {
        try {
            List<Grade> grades = gradeService.getGradesByStudentCpfAndDiscipline(cpf, disciplineId);
            if (grades.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(grades);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}