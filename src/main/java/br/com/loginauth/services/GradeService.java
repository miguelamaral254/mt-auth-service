package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Grade;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.dto.CreateGradeDTO;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.repositories.GradeRepository;
import br.com.loginauth.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;

    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
    }

    public Grade createGrade(CreateGradeDTO createGradeDTO) {
        List<Grade> existingGrades = gradeRepository.findByStudentCpfAndDisciplineIdAndEvaluationType(
                createGradeDTO.studentCpf(),
                createGradeDTO.disciplineId(),
                createGradeDTO.evaluationType()
        );

        if (!existingGrades.isEmpty()) {
            Grade existingGrade = existingGrades.get(0);
            existingGrade.setEvaluation(createGradeDTO.evaluation());
            existingGrade.setEvaluationDate(LocalDateTime.now());
            return gradeRepository.save(existingGrade);
        } else {
            Grade grade = new Grade();
            grade.setStudentCpf(createGradeDTO.studentCpf());
            grade.setDisciplineId(createGradeDTO.disciplineId());
            grade.setEvaluation(createGradeDTO.evaluation());
            grade.setEvaluationType(createGradeDTO.evaluationType());
            grade.setEvaluationDate(LocalDateTime.now());
            return gradeRepository.save(grade);
        }
    }

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public List<Grade> getGradesByStudentCpf(String cpf) {
        Optional<User> studentOpt = studentRepository.findByCpf(cpf);
        if (studentOpt.isEmpty()) {
            throw new StudentNotFoundException("Student not found with CPF " + cpf);
        }
        return gradeRepository.findAll().stream()
                .filter(grade -> grade.getStudentCpf().equals(cpf))
                .collect(Collectors.toList());
    }

    public List<Grade> getGradesByStudentCpfAndDiscipline(String cpf, Long disciplineId) {
        Optional<User> studentOpt = studentRepository.findByCpf(cpf);
        if (studentOpt.isEmpty()) {
            throw new StudentNotFoundException("Student not found with CPF " + cpf);
        }
        return gradeRepository.findAll().stream()
                .filter(grade -> grade.getStudentCpf().equals(cpf) && grade.getDisciplineId().equals(disciplineId))
                .collect(Collectors.toList());
    }
}
