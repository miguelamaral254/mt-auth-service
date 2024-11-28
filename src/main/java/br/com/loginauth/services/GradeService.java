package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Grade;
import br.com.loginauth.domain.entities.Notification;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Letter;
import br.com.loginauth.dto.CreateGradeDTO;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.repositories.GradeRepository;
import br.com.loginauth.repositories.NotificationRepository;
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
    private final NotificationRepository notificationRepository;
    private final DisciplineService disciplineService;

    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository, NotificationRepository notificationRepository, DisciplineService disciplineService) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.notificationRepository = notificationRepository;
        this.disciplineService = disciplineService;
    }

    public Grade createGrade(CreateGradeDTO createGradeDTO) {
        // Verifica se a nota já existe
        List<Grade> existingGrades = gradeRepository.findByStudentCpfAndDisciplineIdAndEvaluationType(
                createGradeDTO.studentCpf(),
                createGradeDTO.disciplineId(),
                createGradeDTO.evaluationType()
        );

        Grade grade;
        if (!existingGrades.isEmpty()) {
            grade = existingGrades.get(0);
            grade.setEvaluation(createGradeDTO.evaluation());
            grade.setEvaluationDate(LocalDateTime.now());
        } else {
            grade = new Grade();
            grade.setStudentCpf(createGradeDTO.studentCpf());
            grade.setDisciplineId(createGradeDTO.disciplineId());
            grade.setEvaluation(createGradeDTO.evaluation());
            grade.setEvaluationType(createGradeDTO.evaluationType());
            grade.setEvaluationDate(LocalDateTime.now());
        }
        Grade savedGrade = gradeRepository.save(grade);

        // Converte nota para conceito (Letter enum)
        Letter concept = convertScoreToLetter(createGradeDTO.evaluation());
        String disciplineName = disciplineService.getDisciplineById(createGradeDTO.disciplineId()).getName();

        studentRepository.findByCpf(createGradeDTO.studentCpf()).ifPresentOrElse(student -> {
            String header = "Nova avaliação atribuída";
            String message = String.format(
                    "Seu novo conceito da %s na disciplina %s agora é %s.",
                    createGradeDTO.evaluationType(),
                    disciplineName,
                    concept
            );

            Notification notification = new Notification();
            notification.setHeader(header);
            notification.setMessage(message);
            notification.setTimestamp(LocalDateTime.now());
            notification.setUser(student);
            notification.setRead(false);

            notificationRepository.save(notification);
        }, () -> {
            throw new StudentNotFoundException("Student not found with CPF " + createGradeDTO.studentCpf());
        });

        return savedGrade;
    }

    // Converte nota para conceito (Letter enum)
    private Letter convertScoreToLetter(double score) {
        if (score >= 9) {
            return Letter.A;
        } else if (score >= 7) {
            return Letter.B;
        } else if (score >= 5) {
            return Letter.C;
        } else if (score >= 4) {
            return Letter.D;
        } else if (score >= 2) {
            return Letter.E;
        } else {
            return Letter.F;
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
