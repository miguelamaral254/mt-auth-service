package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Professor;
import br.com.loginauth.domain.enums.Role;
import br.com.loginauth.dto.ProfessorDTO;
import br.com.loginauth.exceptions.ProfessorNotFoundException;
import br.com.loginauth.exceptions.UserAlreadyExistsException;
import br.com.loginauth.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProfessorServiceTest {

    @InjectMocks
    private ProfessorService professorService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerProfessor_UserDoesNotExist_SavesProfessorWithRole() {
        ProfessorDTO professorDTO = new ProfessorDTO("John Doe", "123456789", "password", true, "john@example.com",
                LocalDate.now(), "123 Street", "1234567890", "REG123", "Mathematics", "PhD");

        when(userRepository.findByCpf(professorDTO.cpf())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(professorDTO.password())).thenReturn("encodedPassword");

        professorService.registerProfessor(professorDTO);

        ArgumentCaptor<Professor> professorCaptor = ArgumentCaptor.forClass(Professor.class);
        verify(userRepository).save(professorCaptor.capture());

        assertEquals(Role.PROFESSOR, professorCaptor.getValue().getRole());
    }


    @Test
    void registerProfessor_UserExists_ThrowsUserAlreadyExistsException() {
        ProfessorDTO professorDTO = new ProfessorDTO("John Doe", "123456789", "password", true, "john@example.com",
                LocalDate.now(), "123 Street", "1234567890", "REG123", "Mathematics", "PhD");

        when(userRepository.findByCpf(professorDTO.cpf())).thenReturn(Optional.of(new Professor()));

        assertThrows(UserAlreadyExistsException.class, () -> professorService.registerProfessor(professorDTO));
    }

    @Test
    void updateProfessor_ExistingProfessor_UpdatesProfessor() {
        ProfessorDTO professorDTO = new ProfessorDTO("John Doe", "123456789", "newPassword", true, "john@example.com",
                LocalDate.now(), "456 Avenue", "0987654321", "REG456", "Physics", "PhD");

        Professor existingProfessor = new Professor();
        existingProfessor.setCpf("123456789");
        existingProfessor.setName("Old Name");

        when(userRepository.findByCpf(professorDTO.cpf())).thenReturn(Optional.of(existingProfessor));

        professorService.updateProfessor("123456789", professorDTO);

        assertEquals("John Doe", existingProfessor.getName());
        assertEquals("456 Avenue", existingProfessor.getAddress());
        verify(userRepository).save(existingProfessor);
    }

    @Test
    void updateProfessor_NonExistingProfessor_ThrowsProfessorNotFoundException() {
        ProfessorDTO professorDTO = new ProfessorDTO("John Doe", "123456789", "password", true, "john@example.com",
                LocalDate.now(), "123 Street", "1234567890", "REG123", "Mathematics", "PhD");

        when(userRepository.findByCpf(professorDTO.cpf())).thenReturn(Optional.empty());

        assertThrows(ProfessorNotFoundException.class, () -> professorService.updateProfessor("123456789", professorDTO));
    }

}
