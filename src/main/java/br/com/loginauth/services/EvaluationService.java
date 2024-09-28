package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Evaluation;
import br.com.loginauth.domain.entities.Grade;
import br.com.loginauth.dto.EvaluationDTO;

import br.com.loginauth.repositories.EvaluationRepository;
import br.com.loginauth.repositories.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private GradeRepository gradeRepository;

    public void createEvaluation(EvaluationDTO evaluationDTO) {
        Grade grade = gradeRepository.findById(evaluationDTO.gradeId())
                .orElseThrow(() -> new IllegalArgumentException("Grade not found"));

        Evaluation evaluation = new Evaluation();
        evaluation.setGrade(grade);
        evaluation.setEvaluationType(evaluationDTO.evaluationType());
        evaluation.setDescription(evaluationDTO.description());
        evaluation.setEvaluation(evaluationDTO.evaluation());

        evaluationRepository.save(evaluation);
    }

    public EvaluationDTO updateEvaluation(Long id, EvaluationDTO evaluationDTO) {
        Optional<Evaluation> evaluationOptional = evaluationRepository.findById(id);

        if (evaluationOptional.isPresent()) {
            Evaluation existingEvaluation = evaluationOptional.get();
            existingEvaluation.setEvaluation(evaluationDTO.evaluation());
            existingEvaluation.setDescription(evaluationDTO.description());
            existingEvaluation.setEvaluationType(evaluationDTO.evaluationType());
            evaluationRepository.save(existingEvaluation);
            return new EvaluationDTO(
                    existingEvaluation.getGrade().getId(),
                    existingEvaluation.getEvaluation(),
                    existingEvaluation.getDescription(),
                    existingEvaluation.getEvaluationType()
            );
        } else {
            throw new RuntimeException("Evaluation not found");
        }
    }

    public List<EvaluationDTO> getEvaluationsByGradeId(Long gradeId) {
        List<Evaluation> evaluations = evaluationRepository.findByGradeId(gradeId);
        return evaluations.stream().map(evaluation -> new EvaluationDTO(
                evaluation.getGrade().getId(),
                evaluation.getEvaluation(),
                evaluation.getDescription(),
                evaluation.getEvaluationType()


        )).toList();
    }

    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }
}
