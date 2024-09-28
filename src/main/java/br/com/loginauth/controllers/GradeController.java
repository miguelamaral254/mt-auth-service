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
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Grade updatedGrade) {
        Grade grade = gradeService.updateGrade(id, updatedGrade);
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @GetMapping("/assessment/{assessmentId}")
    public ResponseEntity<List<Grade>> getGradesByAssessment(@PathVariable Long assessmentId) {
        List<Grade> grades = gradeService.getGradesByAssessmentId(assessmentId);
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.ok("Grade deleted successfully");
    }
}
