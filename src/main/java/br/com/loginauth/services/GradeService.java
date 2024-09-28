package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Evaluation;
import br.com.loginauth.domain.entities.Grade;
import br.com.loginauth.domain.entities.Assessments;
import br.com.loginauth.dto.GradeDTO;
import br.com.loginauth.repositories.AssessmentsRepository;
import br.com.loginauth.repositories.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private AssessmentsRepository assessmentsRepository;

    public void createGrade(GradeDTO gradeDTO) {
        Assessments assessments = assessmentsRepository.findById(gradeDTO.assessmentsId())
                .orElseThrow(() -> new IllegalArgumentException("Assessment not found"));

        Grade grade = new Grade();
        grade.setAssessments(assessments);
        grade.setEvaluations(gradeDTO.evaluations());
        grade.setSemester(gradeDTO.semester());
        grade.setFinalGrade(gradeDTO.finalGrade());

        gradeRepository.save(grade);
    }

    public Grade updateGrade(Long id, Grade updatedGrade) {
        Optional<Grade> gradeOptional = gradeRepository.findById(id);

        if (gradeOptional.isPresent()) {
            Grade existingGrade = gradeOptional.get();
            existingGrade.setFinalGrade(updatedGrade.getFinalGrade());
            existingGrade.setEvaluations(updatedGrade.getEvaluations());
            existingGrade.setSemester(updatedGrade.getSemester());
            return gradeRepository.save(existingGrade);
        } else {
            throw new RuntimeException("Grade not found");
        }
    }

    public List<Grade> getGradesByAssessmentId(Long assessmentsId) {
        return gradeRepository.findByAssessmentsId(assessmentsId);
    }

    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }
}
