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
        // Criando a nova grade
        Grade grade = new Grade();
        grade.setStudentCpf(createGradeDTO.studentCpf());
        grade.setDisciplineId(createGradeDTO.disciplineId());
        grade.setEvaluation(createGradeDTO.evaluation());
        grade.setEvaluationType(createGradeDTO.evaluationType());
        grade.setEvaluationDate(LocalDateTime.now()); // Data atual

        // Salvando a nova grade no banco de dados
        return gradeRepository.save(grade);
    }


    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }


    public List<Grade> getGradesByStudentCpf(String cpf) {
        // Verificando se o estudante existe
        Optional<User> studentOpt = studentRepository.findByCpf(cpf);
        if (studentOpt.isEmpty()) {
            throw new StudentNotFoundException("Student not found with CPF " + cpf);
        }

        // Buscando todas as grades
        List<Grade> grades = gradeRepository.findAll();

        // Filtrando as grades que pertencem ao estudante com o CPF fornecido
        return grades.stream()
                .filter(grade -> grade.getStudentCpf().equals(cpf))
                .collect(Collectors.toList());
    }
    public List<Grade> getGradesByStudentCpfAndDiscipline(String cpf, Long disciplineId) {
        // Verificando se o estudante existe
        Optional<User> studentOpt = studentRepository.findByCpf(cpf);
        if (studentOpt.isEmpty()) {
            throw new StudentNotFoundException("Student not found with CPF " + cpf);
        }

        // Buscando todas as grades
        List<Grade> grades = gradeRepository.findAll();

        // Filtrando as grades que pertencem ao estudante com o CPF fornecido e à disciplina específica
        return grades.stream()
                .filter(grade -> grade.getStudentCpf().equals(cpf) && grade.getDisciplineId().equals(disciplineId))
                .collect(Collectors.toList());
    }

}
