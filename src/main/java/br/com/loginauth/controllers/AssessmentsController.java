package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.Assessments;
import br.com.loginauth.dto.AssessmentsDTO;
import br.com.loginauth.services.AssessmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assessments")
public class AssessmentsController {

    @Autowired
    private AssessmentsService assessmentsService;

    @PostMapping
    public ResponseEntity<String> createAssessment(@RequestBody AssessmentsDTO assessmentsDTO) {
        assessmentsService.createAssessment(assessmentsDTO);
        return ResponseEntity.ok("Assessment created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assessments> updateAssessment(@PathVariable Long id, @RequestBody AssessmentsDTO assessmentsDTO) {
        Assessments updatedAssessment = assessmentsService.updateAssessment(id, assessmentsDTO);
        return new ResponseEntity<>(updatedAssessment, HttpStatus.OK);
    }

    @GetMapping("/student/{cpf}")
    public ResponseEntity<List<Assessments>> getAssessmentsByStudent(@PathVariable String cpf) {
        List<Assessments> assessments = assessmentsService.getAssessmentsByStudentCpf(cpf);
        return new ResponseEntity<>(assessments, HttpStatus.OK);
    }

    @GetMapping("/discipline/{id}")
    public ResponseEntity<List<Assessments>> getAssessmentsByDiscipline(@PathVariable Long id) {
        List<Assessments> assessments = assessmentsService.getAssessmentsByDisciplineId(id);
        return new ResponseEntity<>(assessments, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssessment(@PathVariable Long id) {
        assessmentsService.deleteAssessment(id);
        return ResponseEntity.ok("Assessment deleted successfully");
    }
}
