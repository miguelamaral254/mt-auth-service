package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Grade;
import br.com.loginauth.repositories.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    public Grade createGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    public Grade updateGrade(Long id, Grade updatedGrade) {
        Optional<Grade> gradeOptional = gradeRepository.findById(id);

        if (gradeOptional.isPresent()) {
            Grade existingGrade = gradeOptional.get();
            existingGrade.setGrade(updatedGrade.getGrade());
            existingGrade.setEvaluationType(updatedGrade.getEvaluationType());
            existingGrade.setEvaluationDescription(updatedGrade.getEvaluationDescription());
            existingGrade.setEvaluationDate(updatedGrade.getEvaluationDate());
            return gradeRepository.save(existingGrade);
        } else {
            throw new RuntimeException("Grade not found");
        }
    }

    public List<Grade> getGradesByStudentCpf(String studentCpf) {
        return gradeRepository.findByStudentCpf(studentCpf);
    }

    public List<Grade> getGradesByDisciplineId(Long disciplineId) {
        return gradeRepository.findByDisciplineId(disciplineId);
    }

    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }
}
