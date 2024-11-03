package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Parent;
import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Role;
import br.com.loginauth.dto.ParentDTO;
import br.com.loginauth.exceptions.ParentNotFoundException;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.exceptions.UserAlreadyExistsException;
import br.com.loginauth.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

class ParentServiceTest {

    @InjectMocks
    private ParentService parentService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
/*
    @Test
    void registerParent_UserDoesNotExist_SavesParentWithRole() {
        ParentDTO parentDTO = new ParentDTO("Jane Doe", "987654321", "password", true, "jane@example.com",
                LocalDateTime.now().toLocalDate(), "456 Avenue", "0987654321", "123456789");

        when(userRepository.findByCpf(parentDTO.cpf())).thenReturn(Optional.empty());
        when(userRepository.findByCpf(parentDTO.studentCpfs())).thenReturn(Optional.of(new Student()));
        when(passwordEncoder.encode(parentDTO.password())).thenReturn("encodedPassword");

        parentService.registerParent(parentDTO);

        ArgumentCaptor<Parent> parentCaptor = ArgumentCaptor.forClass(Parent.class);
        verify(userRepository).save(parentCaptor.capture());

        assertEquals(Role.PARENT, parentCaptor.getValue().getRole());
    }
*
    @Test
    void registerParent_UserExists_ThrowsUserAlreadyExistsException() {
        ParentDTO parentDTO = new ParentDTO("Jane Doe", "987654321", "password", true, "jane@example.com",
                LocalDateTime.now().toLocalDate(), "456 Avenue", "0987654321", "123456789");

        when(userRepository.findByCpf(parentDTO.cpf())).thenReturn(Optional.of(new Parent()));

        assertThrows(UserAlreadyExistsException.class, () -> parentService.registerParent(parentDTO));
    }

    @Test
    void registerParent_StudentDoesNotExist_ThrowsIllegalArgumentException() {
        ParentDTO parentDTO = new ParentDTO("Jane Doe", "987654321", "password", true, "jane@example.com",
                LocalDateTime.now().toLocalDate(), "456 Avenue", "0987654321", "123456789");

        when(userRepository.findByCpf(parentDTO.cpf())).thenReturn(Optional.empty());
        when(userRepository.findByCpf(parentDTO.studentCpf())).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> parentService.registerParent(parentDTO));
    }

    @Test
    void updateParent_ExistingParent_UpdatesParent() {
        ParentDTO parentDTO = new ParentDTO("Jane Doe", "987654321", "newPassword", true, "jane.new@example.com",
                LocalDateTime.now().toLocalDate(), "789 Boulevard", "1122334455", "123456789");

        Parent existingParent = new Parent();
        existingParent.setCpf("987654321");
        existingParent.setName("Old Name");

        when(userRepository.findByCpf(parentDTO.cpf())).thenReturn(Optional.of(existingParent));

        parentService.updateParent("987654321", parentDTO);

        assertEquals("Jane Doe", existingParent.getName());
        assertEquals("789 Boulevard", existingParent.getAddress());
        verify(userRepository).save(existingParent);
    }

    @Test
    void updateParent_NonExistingParent_ThrowsIllegalArgumentException() {
        ParentDTO parentDTO = new ParentDTO("Jane Doe", "987654321", "password", true, "jane@example.com",
                LocalDateTime.now().toLocalDate(), "456 Avenue", "0987654321", "123456789");

        when(userRepository.findByCpf(parentDTO.cpf())).thenReturn(Optional.empty());

        assertThrows(ParentNotFoundException.class, () -> parentService.updateParent("987654321", parentDTO));
    }

 */
}
