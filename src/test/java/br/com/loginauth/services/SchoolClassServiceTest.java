package br.com.loginauth.services;

import br.com.loginauth.domain.entities.SchoolClass;
import br.com.loginauth.domain.enums.Letter;
import br.com.loginauth.domain.enums.Shift;
import br.com.loginauth.domain.enums.TechnicalCourse;
import br.com.loginauth.domain.enums.Year;
import br.com.loginauth.dto.SchoolClassDTO;
import br.com.loginauth.exceptions.SchoolClassNotFoundException;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.repositories.SchoolClassRepository;
import br.com.loginauth.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SchoolClassServiceTest {

    @InjectMocks
    private SchoolClassService schoolClassService;

    @Mock
    private SchoolClassRepository schoolClassRepository;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addStudentToClass_SchoolClassNotFound_ThrowsSchoolClassNotFoundException() {
        String studentCpf = "123456789";
        Long schoolClassId = 1L;

        when(schoolClassRepository.findById(schoolClassId)).thenReturn(Optional.empty());

        assertThrows(SchoolClassNotFoundException.class, () -> {
            schoolClassService.addStudentToClass(schoolClassId, studentCpf);
        });
    }

    @Test
    void addStudentToClass_StudentNotFound_ThrowsStudentNotFoundException() {
        String studentCpf = "123456789";
        Long schoolClassId = 1L;

        SchoolClass schoolClass = new SchoolClass();
        when(schoolClassRepository.findById(schoolClassId)).thenReturn(Optional.of(schoolClass));
        when(studentRepository.findByCpf(studentCpf)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> {
            schoolClassService.addStudentToClass(schoolClassId, studentCpf);
        });
    }

    @Test
    void createClass_ShouldReturnSavedSchoolClass() {
        SchoolClassDTO schoolClassDTO = new SchoolClassDTO(1L, Letter.A, Shift.MORNING, "A101", TechnicalCourse.TDS, Year.FIRST, new Date());
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(schoolClassDTO.id());
        schoolClass.setLetter(schoolClassDTO.letter());
        schoolClass.setCode(schoolClassDTO.code());
        schoolClass.setTechnicalCourse(schoolClassDTO.technicalCourse());
        schoolClass.setYear(schoolClassDTO.year());
        schoolClass.setDate(schoolClassDTO.date());

        when(schoolClassRepository.save(any(SchoolClass.class))).thenReturn(schoolClass);

        SchoolClass result = schoolClassService.createClass(schoolClassDTO);

        assertNotNull(result);
        assertEquals(schoolClassDTO.letter(), result.getLetter()); // Corrigido para letter
        assertEquals(schoolClassDTO.code(), result.getCode());
        verify(schoolClassRepository, times(1)).save(any(SchoolClass.class));
    }

    @Test
    void getAllClasses_ShouldReturnListOfSchoolClasses() {
        SchoolClass class1 = new SchoolClass();
        class1.setLetter(Letter.A);
        class1.setCode("A101");

        SchoolClass class2 = new SchoolClass();
        class2.setLetter(Letter.B);
        class2.setCode("B102");

        when(schoolClassRepository.findAll()).thenReturn(Arrays.asList(class1, class2));

        List<SchoolClass> result = schoolClassService.getAllClasses();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(Letter.A, result.get(0).getLetter());
        assertEquals("A101", result.get(0).getCode());
        assertEquals(Letter.B, result.get(1).getLetter());
        assertEquals("B102", result.get(1).getCode());
        verify(schoolClassRepository, times(1)).findAll();
    }

    @Test
    void getClassById_ShouldReturnSchoolClass_WhenClassExists() {
        Long classId = 1L;
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(classId);
        schoolClass.setLetter(Letter.A);
        schoolClass.setCode("A101");

        when(schoolClassRepository.findById(classId)).thenReturn(Optional.of(schoolClass));

        SchoolClass result = schoolClassService.getClassById(classId);

        assertNotNull(result);
        assertEquals(classId, result.getId());
        assertEquals(Letter.A, result.getLetter());
        assertEquals("A101", result.getCode());
        verify(schoolClassRepository, times(1)).findById(classId);
    }

    @Test
    void getClassById_ShouldThrowSchoolClassNotFoundException_WhenClassDoesNotExist() {
        Long classId = 1L;

        when(schoolClassRepository.findById(classId)).thenReturn(Optional.empty());

        assertThrows(SchoolClassNotFoundException.class, () -> {
            schoolClassService.getClassById(classId);
        });

        verify(schoolClassRepository, times(1)).findById(classId);
    }
}
