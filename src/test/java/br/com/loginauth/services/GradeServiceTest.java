package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Grade;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.EvaluationType;
import br.com.loginauth.dto.CreateGradeDTO;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.repositories.GradeRepository;
import br.com.loginauth.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private GradeService gradeService;

    private CreateGradeDTO createGradeDTO;
    private User student;
    private Grade grade;

    @BeforeEach
    void setUp() {
        createGradeDTO = new CreateGradeDTO("123456789", 1L, 10.0, EvaluationType.AV1, LocalDateTime.now());
        student = new User();
        student.setCpf("123456789");
        grade = new Grade();
        grade.setStudentCpf(createGradeDTO.studentCpf());
        grade.setDisciplineId(createGradeDTO.disciplineId());
        grade.setEvaluation(createGradeDTO.evaluation());
        grade.setEvaluationType(createGradeDTO.evaluationType());
        grade.setEvaluationDate(createGradeDTO.evaluationDate());
    }

    @Test
    void testCreateGradeWhenGradeExists() {
        when(gradeRepository.findByStudentCpfAndDisciplineIdAndEvaluationType(
                createGradeDTO.studentCpf(),
                createGradeDTO.disciplineId(),
                createGradeDTO.evaluationType()))
                .thenReturn(List.of(grade));

        when(gradeRepository.save(grade)).thenReturn(grade);

        Grade createdGrade = gradeService.createGrade(createGradeDTO);

        assertNotNull(createdGrade);
        assertEquals(createGradeDTO.evaluation(), createdGrade.getEvaluation());
        assertEquals(createGradeDTO.studentCpf(), createdGrade.getStudentCpf());
        verify(gradeRepository, times(1)).save(grade);
    }

    @Test
    void testCreateGradeWhenGradeDoesNotExist() {
        when(gradeRepository.findByStudentCpfAndDisciplineIdAndEvaluationType(
                createGradeDTO.studentCpf(),
                createGradeDTO.disciplineId(),
                createGradeDTO.evaluationType()))
                .thenReturn(List.of());

        when(gradeRepository.save(any(Grade.class))).thenReturn(grade);

        Grade createdGrade = gradeService.createGrade(createGradeDTO);

        assertNotNull(createdGrade);
        assertEquals(createGradeDTO.evaluation(), createdGrade.getEvaluation());
        verify(gradeRepository, times(1)).save(any(Grade.class));
    }

    @Test
    void testGetGradesByStudentCpfWhenStudentNotFound() {
        when(studentRepository.findByCpf("123456789")).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> gradeService.getGradesByStudentCpf("123456789"));
    }

    @Test
    void testGetGradesByStudentCpfWhenGradesExist() {
        when(studentRepository.findByCpf("123456789")).thenReturn(Optional.of(student));
        when(gradeRepository.findAll()).thenReturn(List.of(grade));

        List<Grade> grades = gradeService.getGradesByStudentCpf("123456789");

        assertEquals(1, grades.size());
        assertEquals("123456789", grades.get(0).getStudentCpf());
    }

    @Test
    void testGetGradesByStudentCpfAndDisciplineWhenStudentNotFound() {
        when(studentRepository.findByCpf("123456789")).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> gradeService.getGradesByStudentCpfAndDiscipline("123456789", 1L));
    }

    @Test
    void testGetGradesByStudentCpfAndDisciplineWhenGradesExist() {
        when(studentRepository.findByCpf("123456789")).thenReturn(Optional.of(student));
        when(gradeRepository.findAll()).thenReturn(List.of(grade));

        List<Grade> grades = gradeService.getGradesByStudentCpfAndDiscipline("123456789", 1L);

        assertEquals(1, grades.size());
        assertEquals("123456789", grades.get(0).getStudentCpf());
    }
}