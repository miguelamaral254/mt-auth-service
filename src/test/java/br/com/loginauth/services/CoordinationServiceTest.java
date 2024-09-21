package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Coordination;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.dto.CoordinationDTO;
import br.com.loginauth.exceptions.UserAlreadyExistsException;
import br.com.loginauth.exceptions.CoordinationNotFoundException;
import br.com.loginauth.repositories.CoordinationRepository;
import br.com.loginauth.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CoordinationServiceTest {

    @InjectMocks
    private CoordinationService coordinationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CoordinationRepository coordinationRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerCoordination_UserDoesNotExist_SavesCoordination() {
        CoordinationDTO coordinationDTO = new CoordinationDTO("12345678900", "password", "John Doe",
                "john@example.com", true, LocalDateTime.now().toLocalDate(),
                "123 Street", "1234567890", "REG123");

        when(userRepository.findByCpf(coordinationDTO.cpf())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(coordinationDTO.password())).thenReturn("encodedPassword");

        coordinationService.registerCoordination(coordinationDTO);

        verify(coordinationRepository).save(any(Coordination.class));
    }

    @Test
    void registerCoordination_UserExists_ThrowsUserAlreadyExistsException() {
        CoordinationDTO coordinationDTO = new CoordinationDTO("12345678900", "password", "John Doe",
                "john@example.com", true, LocalDateTime.now().toLocalDate(),
                "123 Street", "1234567890", "REG123");

        when(userRepository.findByCpf(coordinationDTO.cpf())).thenReturn(Optional.of(new Coordination()));

        assertThrows(UserAlreadyExistsException.class, () -> coordinationService.registerCoordination(coordinationDTO));
    }

    @Test
    void updateCoordination_ExistingCoordination_UpdatesCoordination() {
        CoordinationDTO coordinationDTO = new CoordinationDTO("12345678900", "newPassword", "Jane Doe",
                "jane@example.com", true, LocalDateTime.now().toLocalDate(),
                "456 Avenue", "0987654321", "REG456");

        Coordination existingCoordination = new Coordination();
        existingCoordination.setCpf("12345678900");
        existingCoordination.setName("Old Name");

        when(userRepository.findByCpf(coordinationDTO.cpf())).thenReturn(Optional.of(existingCoordination));

        coordinationService.updateCoordination("12345678900", coordinationDTO);

        assertEquals("Jane Doe", existingCoordination.getName());
        assertEquals("456 Avenue", existingCoordination.getAddress());
        verify(coordinationRepository).save(existingCoordination);
    }

    @Test
    void updateCoordination_NonExistingCoordination_ThrowsCoordinationNotFoundException() {
        CoordinationDTO coordinationDTO = new CoordinationDTO("12345678900", "password", "John Doe",
                "john@example.com", true, LocalDateTime.now().toLocalDate(),
                "123 Street", "1234567890", "REG123");

        when(userRepository.findByCpf(coordinationDTO.cpf())).thenReturn(Optional.empty());

        assertThrows(CoordinationNotFoundException.class, () -> coordinationService.updateCoordination("12345678900", coordinationDTO));
    }
}
