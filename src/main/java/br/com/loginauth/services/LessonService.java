package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Discipline;
import br.com.loginauth.domain.entities.Lesson;
import br.com.loginauth.domain.entities.Professor;
import br.com.loginauth.domain.entities.SchoolClass;
import br.com.loginauth.dto.LessonDTO;
import br.com.loginauth.dto.DisciplineDTO;
import br.com.loginauth.dto.ProfessorDTO;
import br.com.loginauth.dto.SchoolClassDTO;
import br.com.loginauth.repositories.DisciplineRepository;
import br.com.loginauth.repositories.LessonRepository;
import br.com.loginauth.repositories.ProfessorRepository;
import br.com.loginauth.repositories.SchoolClassRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    public LessonDTO createLesson(LessonDTO lessonDTO) {
        // Buscando as entidades existentes no banco de dados
        Discipline discipline = disciplineRepository.findById(lessonDTO.discipline().id())
                .orElseThrow(() -> new EntityNotFoundException("Discipline not found with id " + lessonDTO.discipline().id()));

        Professor professor = (Professor) professorRepository.findByCpf(lessonDTO.professor().cpf())
                .orElseThrow(() -> new EntityNotFoundException("Professor not found with CPF " + lessonDTO.professor().cpf()));

        SchoolClass schoolClass = schoolClassRepository.findById(lessonDTO.schoolClass().id())
                .orElseThrow(() -> new EntityNotFoundException("SchoolClass not found with id " + lessonDTO.schoolClass().id()));

        // Criando a nova lição
        Lesson lesson = new Lesson();
        lesson.setStartTime(lessonDTO.startTime());
        lesson.setEndTime(lessonDTO.endTime());
        lesson.setRoom(lessonDTO.room());
        lesson.setDiscipline(discipline);
        lesson.setProfessor(professor);
        lesson.setSchoolClass(schoolClass);

        // Salvando e retornando o DTO
        return mapToDTO(lessonRepository.save(lesson));
    }

    public LessonDTO getLessonById(Long id) {
        return lessonRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id " + id));
    }

    public List<LessonDTO> getAllLessons() {
        List<Lesson> lessons = lessonRepository.findAll();
        return lessons.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private LessonDTO mapToDTO(Lesson lesson) {
        return new LessonDTO(
                lesson.getId(),
                new SchoolClassDTO(lesson.getSchoolClass().getId(), lesson.getSchoolClass().getName()),
                new DisciplineDTO(
                        lesson.getDiscipline().getId(),
                        lesson.getDiscipline().getName(),
                        lesson.getDiscipline().getWorkload(),
                        lesson.getDiscipline().getDescription()
                ),
                new ProfessorDTO(
                        lesson.getProfessor().getName(),
                        lesson.getProfessor().getCpf(),
                        lesson.getProfessor().getPassword(),
                        lesson.getProfessor().isActive(),
                        lesson.getProfessor().getEmail(),
                        lesson.getProfessor().getBirthDate().toLocalDate(),
                        lesson.getProfessor().getAddress(),
                        lesson.getProfessor().getPhone(),
                        lesson.getProfessor().getRegistration(),
                        lesson.getProfessor().getExpertiseArea(),
                        lesson.getProfessor().getAcademicTitle()
                ),
                lesson.getStartTime(),
                lesson.getEndTime(),
                lesson.getRoom()
        );
    }
}