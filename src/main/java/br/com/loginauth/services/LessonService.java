package br.com.loginauth.services;

import br.com.loginauth.domain.entities.*;
import br.com.loginauth.dto.*;
import br.com.loginauth.exceptions.*;
import br.com.loginauth.repositories.*;
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
        checkForConflicts(lessonDTO);

        Lesson lesson = mapToEntity(new Lesson(), lessonDTO);
        Lesson savedLesson = lessonRepository.save(lesson);

        return mapToDTO(savedLesson);
    }

    private void checkForConflicts(LessonDTO lessonDTO) {
        Optional<Lesson> existingLessonByClass = lessonRepository.findByWeekDayAndStartTimeAndSchoolClassId(
                lessonDTO.weekDay(), lessonDTO.startTime(), lessonDTO.schoolClass().id());

        if (existingLessonByClass.isPresent()) {
            throw new LessonConflictException("Já existe uma aula para a turma no dia "
                    + lessonDTO.weekDay() + " às " + lessonDTO.startTime() + " para a turma " + lessonDTO.schoolClass().id());
        }

        Optional<Lesson> existingLessonByProfessor = lessonRepository.findByWeekDayAndStartTimeAndProfessorCpf(
                lessonDTO.weekDay(), lessonDTO.startTime(), lessonDTO.professor().cpf());

        if (existingLessonByProfessor.isPresent()) {
            throw new LessonConflictException("O professor " + lessonDTO.professor().cpf() +
                    " já possui uma aula no dia " + lessonDTO.weekDay() + " às " + lessonDTO.startTime());
        }
    }

    private Lesson mapToEntity(Lesson lesson, LessonDTO lessonDTO) {
        Discipline discipline = disciplineRepository.findById(lessonDTO.discipline().id())
                .orElseThrow(() -> new DisciplineNotFoundException("Disciplina não encontrada com o ID " + lessonDTO.discipline().id()));

        Professor professor = (Professor) professorRepository.findByCpf(lessonDTO.professor().cpf())
                .orElseThrow(() -> new ProfessorNotFoundException("Professor não encontrado com o CPF " + lessonDTO.professor().cpf()));

        SchoolClass schoolClass = schoolClassRepository.findById(lessonDTO.schoolClass().id())
                .orElseThrow(() -> new SchoolClassNotFoundException("Turma não encontrada com o ID " + lessonDTO.schoolClass().id()));

        lesson.setName(lessonDTO.name());
        lesson.setWeekDay(lessonDTO.weekDay());
        lesson.setStartTime(lessonDTO.startTime());
        lesson.setEndTime(lessonDTO.endTime());
        lesson.setRoom(lessonDTO.room());
        lesson.setDiscipline(discipline);
        lesson.setProfessor(professor);
        lesson.setSchoolClass(schoolClass);

        return lesson;
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
                .orElseThrow(() -> new LessonNotFoundException("Aula não encontrada com o ID " + id));
        return mapToDTO(lesson);
    }

    public List<LessonDTO> getAllLessons() {
        return lessonRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public LessonDTO getLessonByName(String name) {
        Lesson lesson = lessonRepository.findByName(name)
                .orElseThrow(() -> new LessonNotFoundException("Aula não encontrada com o nome " + name));
        return mapToDTO(lesson);
    }

    public LessonDTO updateLesson(Long id, LessonDTO lessonDTO) {
        Lesson existingLesson = lessonRepository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException("Aula não encontrada com o ID " + id));

        checkForConflicts(lessonDTO);
        Lesson updatedLesson = mapToEntity(existingLesson, lessonDTO);
        lessonRepository.save(updatedLesson);

        return mapToDTO(updatedLesson);
    }
}