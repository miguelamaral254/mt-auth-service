package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Lesson;
import br.com.loginauth.domain.entities.Professor;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Role;
import br.com.loginauth.dto.*;
import br.com.loginauth.exceptions.ProfessorNotFoundException;
import br.com.loginauth.repositories.LessonRepository;
import br.com.loginauth.repositories.ProfessorRepository;
import br.com.loginauth.repositories.UserRepository;
import br.com.loginauth.exceptions.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final ProfessorRepository professorRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    public void registerProfessor(ProfessorDTO body) {
        Optional<User> user = repository.findByCpf(body.cpf());

        if (user.isEmpty()) {
            Professor newProfessor = new Professor();
            newProfessor.setCpf(body.cpf());
            newProfessor.setPassword(passwordEncoder.encode(body.password()));
            newProfessor.setName(body.name());
            newProfessor.setEmail(body.email());
            newProfessor.setRole(Role.PROFESSOR);
            newProfessor.setActive(body.active());
            newProfessor.setCreateDate(LocalDateTime.now());
            newProfessor.setBirthDate(Date.valueOf(String.valueOf(body.birthDate())));
            newProfessor.setAddress(body.address());
            newProfessor.setPhone(body.phone());
            newProfessor.setRegistration(body.registration());
            newProfessor.setExpertiseArea(body.expertiseArea());
            newProfessor.setAcademicTitle(body.academicTitle());

            repository.save(newProfessor);
        } else {
            throw new UserAlreadyExistsException("User already exists");
        }
    }

    public Optional<User> findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }


    public void updateProfessor(String cpf, ProfessorDTO body) {
        User existingUser = repository.findByCpf(cpf)
                .orElseThrow(() -> new ProfessorNotFoundException("Professor not found"));

        if (existingUser instanceof Professor) {
            Professor professor = (Professor) existingUser;
            professor.setName(body.name());
            professor.setEmail(body.email());
            professor.setActive(body.active());
            professor.setBirthDate(Date.valueOf(body.birthDate()));
            professor.setAddress(body.address());
            professor.setPhone(body.phone());
            professor.setRegistration(body.registration());
            professor.setExpertiseArea(body.expertiseArea());
            professor.setAcademicTitle(body.academicTitle());
            repository.save(professor);
        } else {
            throw new ProfessorNotFoundException("User is not a professor");
        }
    }
    public List<Professor> findAllProfessors() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.PROFESSOR)
                .map(user -> {
                    Professor professor = new Professor();
                    professor.setCpf(user.getCpf());
                    professor.setName(user.getName());
                    professor.setEmail(user.getEmail());
                    professor.setPassword(user.getPassword());
                    professor.setActive(user.isActive());
                    professor.setCreateDate(user.getCreateDate());
                    return professor;
                })
                .collect(Collectors.toList());
    }

    public List<DisciplineWithClassDTO> getDisciplinesByProfessorCpf(String cpf) {
        Optional<User> professorOpt = professorRepository.findByCpf(cpf);
        if (professorOpt.isEmpty()) {
            throw new ProfessorNotFoundException("Professor not found with CPF " + cpf);
        }

        List<Lesson> lessons = lessonRepository.findAll();

        return lessons.stream()
                .filter(lesson -> lesson.getProfessor().getCpf().equals(cpf))
                .map(lesson -> new DisciplineWithClassDTO(
                        lesson.getDiscipline().getId(),
                        lesson.getDiscipline().getName(),
                        lesson.getDiscipline().getWorkload(),
                        lesson.getDiscipline().getDescription(),
                        new SchoolClassDTO(
                                lesson.getSchoolClass().getId(),
                                lesson.getSchoolClass().getLetter(),
                                lesson.getSchoolClass().getShift(),
                                lesson.getSchoolClass().getCode(),
                                lesson.getSchoolClass().getTechnicalCourse(),
                                lesson.getSchoolClass().getYear(),
                                lesson.getSchoolClass().getDate()
                        )
                ))
                .distinct()
                .collect(Collectors.toList());
    }
    public List<LessonDTO> getLessonsByProfessorCpf(String cpf) {
        Optional<User> professorOpt = professorRepository.findByCpf(cpf);
        if (professorOpt.isEmpty()) {
            throw new ProfessorNotFoundException("Professor not found with CPF " + cpf);
        }

        User professor = professorOpt.get();

        ProfessorResponseDTO professorResponse = new ProfessorResponseDTO(
                professor.getName(),
                professor.getCpf()
        );

        List<Lesson> lessons = lessonRepository.findAll().stream()
                .filter(lesson -> lesson.getProfessor().getCpf().equals(cpf))
                .collect(Collectors.toList());

        return lessons.stream()
                .map(lesson -> new LessonDTO(
                        lesson.getId(),
                        lesson.getName(),
                        new SchoolClassDTO(
                                lesson.getSchoolClass().getId(),
                                lesson.getSchoolClass().getLetter(),
                                lesson.getSchoolClass().getShift(),
                                lesson.getSchoolClass().getCode(),
                                lesson.getSchoolClass().getTechnicalCourse(),
                                lesson.getSchoolClass().getYear(),
                                lesson.getSchoolClass().getDate()
                        ),
                        new DisciplineDTO(
                                lesson.getDiscipline().getId(),
                                lesson.getDiscipline().getName(),
                                lesson.getDiscipline().getWorkload(),
                                lesson.getDiscipline().getDescription()
                        ),
                        professorResponse,
                        lesson.getWeekDay(),
                        lesson.getStartTime(),
                        lesson.getEndTime(),
                        lesson.getRoom(),
                        lesson.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }


}
