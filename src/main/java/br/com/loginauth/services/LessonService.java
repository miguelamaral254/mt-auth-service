package br.com.loginauth.services;

import br.com.loginauth.domain.entities.*;
import br.com.loginauth.dto.LessonDTO;
import br.com.loginauth.dto.DisciplineDTO;
import br.com.loginauth.dto.ProfessorDTO;
import br.com.loginauth.dto.SchoolClassDTO;
import br.com.loginauth.exceptions.DisciplineNotFoundException;
import br.com.loginauth.exceptions.ProfessorNotFoundException;
import br.com.loginauth.exceptions.SchoolClassNotFoundException;
import br.com.loginauth.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    @Autowired
    private StudentDisciplineRepository studentDisciplineRepository;

    public LessonDTO createLesson(LessonDTO lessonDTO) {
        Discipline discipline = disciplineRepository.findById(lessonDTO.discipline().id())
                .orElseThrow(() -> new DisciplineNotFoundException("Discipline not found with id " + lessonDTO.discipline().id()));

        Professor professor = (Professor) professorRepository.findByCpf(lessonDTO.professor().cpf())
                .orElseThrow(() -> new ProfessorNotFoundException("Professor not found with CPF " + lessonDTO.professor().cpf()));

        SchoolClass schoolClass = schoolClassRepository.findById(lessonDTO.schoolClass().id())
                .orElseThrow(() -> new SchoolClassNotFoundException("SchoolClass not found with id " + lessonDTO.schoolClass().id()));

        Lesson lesson = new Lesson();
        lesson.setStartTime(lessonDTO.startTime());
        lesson.setEndTime(lessonDTO.endTime());
        lesson.setRoom(lessonDTO.room());
        lesson.setDiscipline(discipline);
        lesson.setProfessor(professor);
        lesson.setSchoolClass(schoolClass);
        lesson.setName(lessonDTO.name());

        // Salve a lição
        Lesson savedLesson = lessonRepository.save(lesson);

        // Vincule todos os alunos da turma à nova lição
        schoolClass.getStudents().forEach(student -> {
            // Verifique se já existe uma associação entre o aluno e a disciplina
            Optional<StudentDiscipline> existingStudentDiscipline = studentDisciplineRepository
                    .findByStudentCpfAndDisciplineId(student.getCpf(), discipline.getId());

            if (existingStudentDiscipline.isEmpty()) {
                StudentDiscipline studentDiscipline = new StudentDiscipline();
                studentDiscipline.setStudent(student);
                studentDiscipline.setDiscipline(discipline); // Assumindo que você queira vincular à disciplina da lição
                studentDisciplineRepository.save(studentDiscipline);
            }
        });

        return mapToDTO(savedLesson);
    }


    private LessonDTO mapToDTO(Lesson lesson) {
        return new LessonDTO(
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
                        lesson.getDiscipline().getWorkload(), // Incluindo a carga horária
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

    public LessonDTO getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id " + id));
        return mapToDTO(lesson);
    }

    public List<LessonDTO> getAllLessons() {
        List<Lesson> lessons = lessonRepository.findAll();
        return lessons.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public LessonDTO getLessonByName(String name) {
        Lesson lesson = lessonRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with name " + name));
        return mapToDTO(lesson);
    }
}
