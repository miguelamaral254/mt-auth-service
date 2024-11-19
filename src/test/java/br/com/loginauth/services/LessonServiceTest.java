package br.com.loginauth.services;

import br.com.loginauth.domain.entities.*;
import br.com.loginauth.domain.enums.*;
import br.com.loginauth.dto.*;
import br.com.loginauth.exceptions.*;
import br.com.loginauth.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LessonServiceTest {

    @InjectMocks
    private LessonService lessonService;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private DisciplineRepository disciplineRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private SchoolClassRepository schoolClassRepository;

    private LessonDTO lessonDTO;
    private Lesson lesson;
    private SchoolClass schoolClass;
    private Discipline discipline;
    private Professor professor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        schoolClass = new SchoolClass(1L, Letter.A, Shift.MORNING, "1234", TechnicalCourse.TDS, Year.FIRST, new java.util.Date(), null);
        discipline = new Discipline(1L, "Mathematics", 40, "Math basics");
        professor = new Professor();
        professor.setCpf("12345678900");
        professor.setName("Dr. John Doe");
        lessonDTO = new LessonDTO(
                null,
                "Math Class",
                new SchoolClassDTO(schoolClass.getId(), schoolClass.getLetter(), schoolClass.getShift(), schoolClass.getCode(), schoolClass.getTechnicalCourse(), schoolClass.getYear(), schoolClass.getDate()),
                new DisciplineDTO(discipline.getId(), discipline.getName(), discipline.getWorkload(), discipline.getDescription()),
                new ProfessorResponseDTO(professor.getName(), professor.getCpf()),
                Week.MONDAY,
                Schedule.EIGHT_TWENTY,
                Schedule.NINE_TEN,
                "Room 101",
                lesson.getCreatedAt()
        );

        lesson = new Lesson(1L, "Math Class", schoolClass, discipline, professor, Week.MONDAY, Schedule.EIGHT_TWENTY, Schedule.NINE_TEN, "Room 101", java.time.LocalDateTime.now());
    }

    @Test
    void testCreateLessonSuccess() {
        when(disciplineRepository.findById(lessonDTO.discipline().id())).thenReturn(Optional.of(discipline));
        when(professorRepository.findByCpf(lessonDTO.professor().cpf())).thenReturn(Optional.of(professor));
        when(schoolClassRepository.findById(lessonDTO.schoolClass().id())).thenReturn(Optional.of(schoolClass));
        when(lessonRepository.findByWeekDayAndStartTimeAndSchoolClassId(any(), any(), any())).thenReturn(Optional.empty());
        when(lessonRepository.findByWeekDayAndStartTimeAndProfessorCpf(any(), any(), any())).thenReturn(Optional.empty());
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        LessonDTO createdLesson = lessonService.createLesson(lessonDTO);

        assertNotNull(createdLesson);
        assertEquals(lessonDTO.name(), createdLesson.name());
        verify(lessonRepository, times(1)).save(any(Lesson.class));
    }

    @Test
    void testCreateLessonConflictForClass() {
        when(lessonRepository.findByWeekDayAndStartTimeAndSchoolClassId(any(), any(), any())).thenReturn(Optional.of(lesson));

        assertThrows(LessonConflictException.class, () -> lessonService.createLesson(lessonDTO));
    }

    @Test
    void testCreateLessonConflictForProfessor() {
        when(lessonRepository.findByWeekDayAndStartTimeAndProfessorCpf(any(), any(), any())).thenReturn(Optional.of(lesson));

        assertThrows(LessonConflictException.class, () -> lessonService.createLesson(lessonDTO));
    }

    @Test
    void testUpdateLessonSuccess() {
        when(lessonRepository.findById(lesson.getId())).thenReturn(Optional.of(lesson));
        when(disciplineRepository.findById(lessonDTO.discipline().id())).thenReturn(Optional.of(discipline));
        when(professorRepository.findByCpf(lessonDTO.professor().cpf())).thenReturn(Optional.of(professor));
        when(schoolClassRepository.findById(lessonDTO.schoolClass().id())).thenReturn(Optional.of(schoolClass));
        when(lessonRepository.findByWeekDayAndStartTimeAndSchoolClassId(any(), any(), any())).thenReturn(Optional.empty());
        when(lessonRepository.findByWeekDayAndStartTimeAndProfessorCpf(any(), any(), any())).thenReturn(Optional.empty());
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        LessonDTO updatedLesson = lessonService.updateLesson(lesson.getId(), lessonDTO);

        assertNotNull(updatedLesson);
        assertEquals(lessonDTO.name(), updatedLesson.name());
        verify(lessonRepository, times(1)).save(any(Lesson.class));
    }

    @Test
    void testUpdateLessonNotFound() {
        when(lessonRepository.findById(lesson.getId())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.updateLesson(lesson.getId(), lessonDTO));
    }

    @Test
    void testGetLessonByIdSuccess() {
        when(lessonRepository.findById(lesson.getId())).thenReturn(Optional.of(lesson));

        LessonDTO foundLesson = lessonService.getLessonById(lesson.getId());

        assertNotNull(foundLesson);
        assertEquals(lesson.getId(), foundLesson.id());
    }

    @Test
    void testGetLessonByIdNotFound() {
        when(lessonRepository.findById(lesson.getId())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.getLessonById(lesson.getId()));
    }

    @Test
    void testGetLessonByNameSuccess() {
        when(lessonRepository.findByName(lesson.getName())).thenReturn(Optional.of(lesson));

        LessonDTO foundLesson = lessonService.getLessonByName(lesson.getName());

        assertNotNull(foundLesson);
        assertEquals(lesson.getName(), foundLesson.name());
    }

    @Test
    void testGetLessonByNameNotFound() {
        when(lessonRepository.findByName(lesson.getName())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.getLessonByName(lesson.getName()));
    }
}