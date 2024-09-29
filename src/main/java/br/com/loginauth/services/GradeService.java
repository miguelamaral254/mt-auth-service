package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Grade;
import br.com.loginauth.domain.entities.StudentDiscipline;
import br.com.loginauth.dto.GradeCreateDTO;
import br.com.loginauth.dto.GradeResponseDTO;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.repositories.GradeRepository;
import br.com.loginauth.repositories.StudentDisciplineRepository;
import br.com.loginauth.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentDisciplineRepository studentDisciplineRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public GradeService(GradeRepository gradeRepository, StudentDisciplineRepository studentDisciplineRepository, StudentRepository studentRepository) {
        this.gradeRepository = gradeRepository;
        this.studentDisciplineRepository = studentDisciplineRepository;
        this.studentRepository = studentRepository;
    }

    // Método para criar uma Grade associada a um StudentDiscipline usando o DTO
    public Grade createGrade(GradeCreateDTO dto) {
        StudentDiscipline studentDiscipline = studentDisciplineRepository.findById(dto.studentDisciplineId())
                .orElseThrow(() -> new RuntimeException("StudentDiscipline not found with ID: " + dto.studentDisciplineId()));

        Grade grade = new Grade();
        grade.setStudentDiscipline(studentDiscipline);
        grade.setAv1(dto.av1());
        grade.setAv2(dto.av2());
        grade.setAv3(dto.av3());
        grade.setAv4(dto.av4());
        grade.setFinalGrade(dto.finalGrade());
        grade.setEvaluationDate(dto.evaluationDate());

        return gradeRepository.save(grade);
    }

    // Método para buscar todas as notas de um aluno usando o CPF
    public List<GradeResponseDTO> getGradesByStudentCpf(String cpf) {
        // Busca o aluno pelo CPF
        var studentOpt = studentRepository.findByCpf(cpf);
        if (studentOpt.isEmpty()) {
            throw new StudentNotFoundException("Student not found with CPF " + cpf);
        }

        // Busca todas as relações de StudentDiscipline pelo CPF do estudante
        List<StudentDiscipline> studentDisciplines = studentDisciplineRepository.findByStudentCpf(cpf);

        // Coleta as notas relacionadas a essas StudentDiscipline
        return studentDisciplines.stream()
                .flatMap(studentDiscipline -> studentDiscipline.getGrades().stream()) // Pega as notas de cada StudentDiscipline
                .map(grade -> new GradeResponseDTO(
                        grade.getId(),
                        grade.getAv1(),
                        grade.getAv2(),
                        grade.getAv3(),
                        grade.getAv4(),
                        grade.getFinalGrade(),
                        grade.getEvaluationDate(),
                        grade.getStudentDiscipline().getDiscipline().getId(),     // ID da disciplina
                        grade.getStudentDiscipline().getDiscipline().getName(),   // Nome da disciplina
                        grade.getStudentDiscipline().getDiscipline().getWorkload() // Carga horária
                ))
                .collect(Collectors.toList());
    }

    // Novo método para buscar notas de um aluno por CPF e ID da disciplina
    public GradeResponseDTO getGradeByStudentCpfAndDisciplineId(String cpf, Long disciplineId) {
        // Verifica se o aluno existe
        var studentOpt = studentRepository.findByCpf(cpf);
        if (studentOpt.isEmpty()) {
            throw new StudentNotFoundException("Student not found with CPF " + cpf);
        }

        // Busca a relação StudentDiscipline pelo CPF e ID da disciplina
        Optional<StudentDiscipline> studentDisciplineOpt = studentDisciplineRepository.findByStudentCpfAndDisciplineId(cpf, disciplineId);
        if (studentDisciplineOpt.isEmpty()) {
            throw new RuntimeException("No StudentDiscipline found for student CPF " + cpf + " and discipline ID " + disciplineId);
        }

        // Obtém a primeira nota (caso haja mais de uma)
        Grade grade = studentDisciplineOpt.get().getGrades().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No grades found for student CPF " + cpf + " and discipline ID " + disciplineId));

        return new GradeResponseDTO(
                grade.getId(),
                grade.getAv1(),
                grade.getAv2(),
                grade.getAv3(),
                grade.getAv4(),
                grade.getFinalGrade(),
                grade.getEvaluationDate(),
                grade.getStudentDiscipline().getDiscipline().getId(),     // ID da disciplina
                grade.getStudentDiscipline().getDiscipline().getName(),   // Nome da disciplina
                grade.getStudentDiscipline().getDiscipline().getWorkload() // Carga horária
        );
    }
}
