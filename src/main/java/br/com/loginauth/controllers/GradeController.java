package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.Grade;
import br.com.loginauth.dto.GradeDTO;
import br.com.loginauth.services.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @PostMapping
    public ResponseEntity<String> createGrade(@RequestBody GradeDTO gradeDTO) {
        gradeService.createGrade(gradeDTO);
        return ResponseEntity.ok("Grade created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Grade grade) {
        Grade updatedGrade = gradeService.updateGrade(id, grade);
        return new ResponseEntity<>(updatedGrade, HttpStatus.OK);
    }

    @GetMapping("/student/{cpf}")
    public ResponseEntity<List<Grade>> getGradesByStudent(@PathVariable String cpf) {
        List<Grade> grades = gradeService.getGradesByStudentCpf(cpf);
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    @GetMapping("/discipline/{id}")
    public ResponseEntity<List<Grade>> getGradesByDiscipline(@PathVariable Long id) {
        List<Grade> grades = gradeService.getGradesByDisciplineId(id);
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }


}
