package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Assessments;
import br.com.loginauth.domain.entities.Discipline;
import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.dto.AssessmentsDTO;
import br.com.loginauth.exceptions.DisciplineNotFoundException;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.repositories.AssessmentsRepository;
import br.com.loginauth.repositories.DisciplineRepository;
import br.com.loginauth.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AssessmentsService {

    @Autowired
    private AssessmentsRepository assessmentsRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    public void createAssessment(AssessmentsDTO assessmentsDTO) {
        Student student = (Student) studentRepository.findByCpf(assessmentsDTO.student().cpf())
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        Discipline discipline = disciplineRepository.findById(assessmentsDTO.discipline().id())
                .orElseThrow(() -> new DisciplineNotFoundException("Discipline not found"));

        Assessments assessments = new Assessments();
        assessments.setStudent(student);
        assessments.setDiscipline(discipline);
        assessments.setFinalGrade(assessmentsDTO.finalGrade());
        assessments.setEvaluationDate(assessmentsDTO.evaluationDate());
        assessments.setSituation(assessmentsDTO.situation());

        assessmentsRepository.save(assessments);
    }

    public Assessments updateAssessment(Long id, AssessmentsDTO assessmentsDTO) {
        Assessments existingAssessment = assessmentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        existingAssessment.setFinalGrade(assessmentsDTO.finalGrade());
        existingAssessment.setSituation(assessmentsDTO.situation());
        existingAssessment.setEvaluationDate(assessmentsDTO.evaluationDate());

        return assessmentsRepository.save(existingAssessment);
    }

    public List<Assessments> getAssessmentsByStudentCpf(String studentCpf) {
        return assessmentsRepository.findByStudentCpf(studentCpf);
    }

    public List<Assessments> getAssessmentsByDisciplineId(Long disciplineId) {
        return assessmentsRepository.findByDisciplineId(disciplineId);
    }

    public void deleteAssessment(Long id) {
        assessmentsRepository.deleteById(id);
    }
}
