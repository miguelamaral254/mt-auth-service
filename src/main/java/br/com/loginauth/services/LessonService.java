package br.com.loginauth.services;

import br.com.loginauth.domain.entities.*;
import br.com.loginauth.dto.*;
import br.com.loginauth.exceptions.DisciplineNotFoundException;
import br.com.loginauth.exceptions.LessonConflictException;
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


    public LessonDTO createLesson(LessonDTO lessonDTO) {
        // Verificar se já existe uma lição com o mesmo dia e horário para a turma especificada
        Optional<Lesson> existingLessonByClass = lessonRepository.findByWeekDayAndStartTimeAndSchoolClassId(
                lessonDTO.weekDay(), lessonDTO.startTime(), lessonDTO.schoolClass().id());

        if (existingLessonByClass.isPresent()) {
            throw new LessonConflictException("Já existe uma aula para a turma no dia "
                    + lessonDTO.weekDay() + " às " + lessonDTO.startTime() + " para a turma " + lessonDTO.schoolClass().id());
        }

        // Verificar se já existe uma lição no mesmo dia e horário em que o professor já está ocupado
        Optional<Lesson> existingLessonByProfessor = lessonRepository.findByWeekDayAndStartTimeAndProfessorCpf(
                lessonDTO.weekDay(), lessonDTO.startTime(), lessonDTO.professor().cpf());

        if (existingLessonByProfessor.isPresent()) {
            throw new LessonConflictException("O professor " + lessonDTO.professor().cpf() +
                    " já possui uma aula no dia " + lessonDTO.weekDay() + " às " + lessonDTO.startTime());
        }

        // Encontrar a disciplina pelo ID fornecido
        Discipline discipline = disciplineRepository.findById(lessonDTO.discipline().id())
                .orElseThrow(() -> new DisciplineNotFoundException("Disciplina não encontrada com o ID " + lessonDTO.discipline().id()));

        // Encontrar o professor pelo CPF fornecido
        Professor professor = (Professor) professorRepository.findByCpf(lessonDTO.professor().cpf())
                .orElseThrow(() -> new ProfessorNotFoundException("Professor não encontrado com o CPF " + lessonDTO.professor().cpf()));

        // Encontrar a turma pelo ID fornecido
        SchoolClass schoolClass = schoolClassRepository.findById(lessonDTO.schoolClass().id())
                .orElseThrow(() -> new SchoolClassNotFoundException("Turma não encontrada com o ID " + lessonDTO.schoolClass().id()));

        // Criar uma nova lição com os dados fornecidos
        Lesson lesson = new Lesson();
        lesson.setStartTime(lessonDTO.startTime());
        lesson.setEndTime(lessonDTO.endTime());
        lesson.setRoom(lessonDTO.room());
        lesson.setDiscipline(discipline);
        lesson.setProfessor(professor);
        lesson.setSchoolClass(schoolClass);
        lesson.setName(lessonDTO.name());
        lesson.setWeekDay(lessonDTO.weekDay());

        // Salvar a nova lição no repositório
        Lesson savedLesson = lessonRepository.save(lesson);

        // Retornar o DTO da lição criada
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
                        lesson.getDiscipline().getWorkload(),
                        lesson.getDiscipline().getDescription()
                ),
                new ProfessorResponseDTO(
                        lesson.getProfessor().getName(),
                        lesson.getProfessor().getCpf()
                ),
                lesson.getWeekDay(),
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
