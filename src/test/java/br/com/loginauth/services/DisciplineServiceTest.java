package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Discipline;
import br.com.loginauth.exceptions.DisciplineNotFoundException;
import br.com.loginauth.repositories.DisciplineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DisciplineServiceTest {

    @InjectMocks
    private DisciplineService disciplineService;

    @Mock
    private DisciplineRepository disciplineRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDiscipline_ShouldReturnSavedDiscipline() {
        Discipline discipline = new Discipline();
        discipline.setName("Math");
        discipline.setWorkload(60);

        when(disciplineRepository.save(any(Discipline.class))).thenReturn(discipline);

        Discipline result = disciplineService.createDiscipline(discipline);

        assertNotNull(result);
        assertEquals("Math", result.getName());
        verify(disciplineRepository, times(1)).save(any(Discipline.class));
    }

    @Test
    void getDisciplineById_ShouldReturnDiscipline_WhenDisciplineExists() {
        Discipline discipline = new Discipline();
        discipline.setId(1L);
        discipline.setName("Math");

        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));

        Discipline result = disciplineService.getDisciplineById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(disciplineRepository, times(1)).findById(1L);
    }

    @Test
    void getDisciplineById_ShouldThrowDisciplineNotFoundException_WhenDisciplineDoesNotExist() {
        when(disciplineRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DisciplineNotFoundException.class, () -> disciplineService.getDisciplineById(1L));

        verify(disciplineRepository, times(1)).findById(1L);
    }

    @Test
    void getAllDisciplines_ShouldReturnListOfDisciplines() {
        Discipline discipline1 = new Discipline();
        discipline1.setName("Math");

        Discipline discipline2 = new Discipline();
        discipline2.setName("Science");

        when(disciplineRepository.findAll()).thenReturn(Arrays.asList(discipline1, discipline2));

        List<Discipline> result = disciplineService.getAllDisciplines();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(disciplineRepository, times(1)).findAll();
    }

    @Test
    void updateDiscipline_ShouldUpdateAndReturnUpdatedDiscipline() {
        Discipline existingDiscipline = new Discipline();
        existingDiscipline.setId(1L);
        existingDiscipline.setName("Math");

        Discipline updatedDiscipline = new Discipline();
        updatedDiscipline.setName("Advanced Math");
        updatedDiscipline.setWorkload(80);
        updatedDiscipline.setDescription("Advanced course");

        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(existingDiscipline));
        when(disciplineRepository.save(existingDiscipline)).thenReturn(existingDiscipline);

        Discipline result = disciplineService.updateDiscipline(1L, updatedDiscipline);

        assertNotNull(result);
        assertEquals("Advanced Math", result.getName());
        assertEquals(80, result.getWorkload());
        assertEquals("Advanced course", result.getDescription());
        verify(disciplineRepository, times(1)).findById(1L);
        verify(disciplineRepository, times(1)).save(existingDiscipline);
    }

    @Test
    void deleteDiscipline_ShouldDeleteDiscipline() {
        Long disciplineId = 1L;

        disciplineService.deleteDiscipline(disciplineId);

        verify(disciplineRepository, times(1)).deleteById(disciplineId);
    }
}
