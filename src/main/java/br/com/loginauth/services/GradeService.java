package br.com.loginauth.services;

import br.com.loginauth.domain.entities.*;
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
    private final ParentService parentService;

    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository, NotificationRepository notificationRepository, DisciplineService disciplineService, ParentService parentService) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.notificationRepository = notificationRepository;
        this.disciplineService = disciplineService;
        this.parentService = parentService;
    }

    public Grade createGrade(CreateGradeDTO createGradeDTO) {
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

        Letter concept = convertScoreToLetter(createGradeDTO.evaluation());
        String disciplineName = disciplineService.getDisciplineById(createGradeDTO.disciplineId()).getName();

        studentRepository.findByCpf(createGradeDTO.studentCpf()).ifPresentOrElse(student -> {
            String studentHeader = "Nova avaliação atribuída";
            String studentMessage = String.format(
                    "Seu novo conceito da %s na disciplina %s agora é %s.",
                    createGradeDTO.evaluationType(),
                    disciplineName,
                    concept
            );

            Notification studentNotification = new Notification();
            studentNotification.setHeader(studentHeader);
            studentNotification.setMessage(studentMessage);
            studentNotification.setTimestamp(LocalDateTime.now());
            studentNotification.setUser(student);
            studentNotification.setRead(false);

            notificationRepository.save(studentNotification);

            notifyParents((Student) student, createGradeDTO, concept, disciplineName);
        }, () -> {
            throw new StudentNotFoundException("Student not found with CPF " + createGradeDTO.studentCpf());
        });

        return savedGrade;
    }

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

    private void notifyParents(Student student, CreateGradeDTO createGradeDTO, Letter concept, String disciplineName) {
        List<User> allUsers = parentService.findAllParents();

        List<Parent> parents = allUsers.stream()
                .filter(user -> user instanceof Parent)
                .map(user -> (Parent) user)
                .filter(parent -> parent.getStudents().stream()
                        .anyMatch(st -> st.getCpf().equals(student.getCpf())))
                .collect(Collectors.toList());

        if (!parents.isEmpty()) {
            for (Parent parent : parents) {
                String parentHeader = "Avaliação do estudante";
                String parentMessage = String.format(
                        "O estudante %s recebeu o conceito %s na avaliação %s da disciplina %s.",
                        student.getName(),
                        concept,
                        createGradeDTO.evaluationType(),
                        disciplineName
                );

                Notification parentNotification = new Notification();
                parentNotification.setHeader(parentHeader);
                parentNotification.setMessage(parentMessage);
                parentNotification.setTimestamp(LocalDateTime.now());
                parentNotification.setUser(parent);
                parentNotification.setRead(false);

                notificationRepository.save(parentNotification);
            }
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
