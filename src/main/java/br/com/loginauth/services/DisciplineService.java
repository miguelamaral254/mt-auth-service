package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Discipline;
import br.com.loginauth.domain.entities.Lesson;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.dto.DisciplineDTO;
import br.com.loginauth.exceptions.DisciplineNotFoundException;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.repositories.DisciplineRepository;
import br.com.loginauth.repositories.LessonRepository;
import br.com.loginauth.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DisciplineService {

    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private LessonRepository lessonRepository;

    public Discipline createDiscipline(Discipline discipline) {
        return disciplineRepository.save(discipline);
    }

    public Discipline getDisciplineById(Long id) {
        return disciplineRepository.findById(id)
                .orElseThrow(() -> new DisciplineNotFoundException("Discipline not found"));
    }
    public List<Discipline> getAllDisciplines() {
        return disciplineRepository.findAll();
    }

    public Discipline updateDiscipline(Long id, Discipline updatedDiscipline) {
        Discipline existingDiscipline = getDisciplineById(id);

        existingDiscipline.setName(updatedDiscipline.getName());
        existingDiscipline.setWorkload(updatedDiscipline.getWorkload());
        existingDiscipline.setDescription(updatedDiscipline.getDescription());

        return disciplineRepository.save(existingDiscipline);
    }

    public List<DisciplineDTO> getDisciplinesByStudentCpf(String cpf) {
        Optional<User> studentOpt = studentRepository.findByCpf(cpf);
        if (studentOpt.isEmpty()) {
            throw new StudentNotFoundException("Student not found with CPF " + cpf);
        }

        List<Lesson> lessons = lessonRepository.findAll();

        return lessons.stream()
                .filter(lesson -> lesson.getSchoolClass().getStudents().stream()
                        .anyMatch(s -> s.getCpf().equals(cpf)))
                .map(lesson -> new DisciplineDTO(
                        lesson.getDiscipline().getId(),
                        lesson.getDiscipline().getName(),
                        lesson.getDiscipline().getWorkload(),
                        lesson.getDiscipline().getDescription()
                ))
                .distinct()
                .collect(Collectors.toList());
    }
    public void deleteDiscipline(Long id) {
        disciplineRepository.deleteById(id);
    }
}
