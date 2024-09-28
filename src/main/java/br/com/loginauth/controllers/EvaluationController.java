package br.com.loginauth.controllers;

import br.com.loginauth.dto.EvaluationDTO;
import br.com.loginauth.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping
    public ResponseEntity<String> createEvaluation(@RequestBody EvaluationDTO evaluationDTO) {
        evaluationService.createEvaluation(evaluationDTO);
        return ResponseEntity.ok("Evaluation created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluationDTO> updateEvaluation(@PathVariable Long id, @RequestBody EvaluationDTO evaluationDTO) {
        EvaluationDTO updatedEvaluation = evaluationService.updateEvaluation(id, evaluationDTO);
        return new ResponseEntity<>(updatedEvaluation, HttpStatus.OK);
    }

    @GetMapping("/grade/{gradeId}")
    public ResponseEntity<List<EvaluationDTO>> getEvaluationsByGrade(@PathVariable Long gradeId) {
        List<EvaluationDTO> evaluations = evaluationService.getEvaluationsByGradeId(gradeId);
        return new ResponseEntity<>(evaluations, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvaluation(@PathVariable Long id) {
        evaluationService.deleteEvaluation(id);
        return ResponseEntity.ok("Evaluation deleted successfully");
    }
}
