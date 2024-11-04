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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void registerParent_UserDoesNotExist_SavesParentWithRole() {
        ParentDTO parentDTO = new ParentDTO("Jane Doe", "987654321", "password", true, "jane@example.com",
                LocalDate.now(), "456 Avenue", "0987654321", List.of("123456789"));

        // Mock CPF do usuário principal (Parent)
        when(userRepository.findByCpf(parentDTO.cpf())).thenReturn(Optional.empty());

        // Mock CPF dos estudantes
        when(userRepository.findByCpf("123456789")).thenReturn(Optional.of(new Student()));

        // Mock da senha
        when(passwordEncoder.encode(parentDTO.password())).thenReturn("encodedPassword");

        parentService.registerParent(parentDTO);

        // Captura e verifica o Parent salvo
        ArgumentCaptor<Parent> parentCaptor = ArgumentCaptor.forClass(Parent.class);
        verify(userRepository).save(parentCaptor.capture());

        Parent savedParent = parentCaptor.getValue();
        assertEquals(Role.PARENT, savedParent.getRole());
        assertEquals("Jane Doe", savedParent.getName());
        assertEquals("encodedPassword", savedParent.getPassword());
        assertEquals("jane@example.com", savedParent.getEmail());
        assertTrue(savedParent.isActive());
    }

    @Test
    void registerParent_UserExists_ThrowsUserAlreadyExistsException() {
        ParentDTO parentDTO = new ParentDTO("Jane Doe", "987654321", "password", true, "jane@example.com",
                LocalDate.now(), "456 Avenue", "0987654321", List.of("123456789"));

        // Simula que o usuário já existe
        when(userRepository.findByCpf(parentDTO.cpf())).thenReturn(Optional.of(new Parent()));

        assertThrows(UserAlreadyExistsException.class, () -> parentService.registerParent(parentDTO));
    }

    @Test
    void registerParent_StudentDoesNotExist_ThrowsStudentNotFoundException() {
        ParentDTO parentDTO = new ParentDTO("Jane Doe", "987654321", "password", true, "jane@example.com",
                LocalDate.now(), "456 Avenue", "0987654321", List.of("123456789"));

        when(userRepository.findByCpf(parentDTO.cpf())).thenReturn(Optional.empty());

        // Simula que o estudante não existe
        when(userRepository.findByCpf("123456789")).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> parentService.registerParent(parentDTO));
    }

    @Test
    void updateParent_ExistingParent_UpdatesParent() {
        ParentDTO parentDTO = new ParentDTO("Jane Doe", "987654321", "newPassword", true, "jane.new@example.com",
                LocalDate.now(), "789 Boulevard", "1122334455", List.of("123456789"));

        Parent existingParent = new Parent();
        existingParent.setCpf("987654321");
        existingParent.setName("Old Name");

        // Simula que o Parent existe
        when(userRepository.findByCpf("987654321")).thenReturn(Optional.of(existingParent));

        // Mock dos estudantes
        when(userRepository.findByCpf("123456789")).thenReturn(Optional.of(new Student()));

        parentService.updateParent("987654321", parentDTO);

        // Verificações
        assertEquals("Jane Doe", existingParent.getName());
        assertEquals("789 Boulevard", existingParent.getAddress());
        assertEquals("jane.new@example.com", existingParent.getEmail());
        verify(userRepository).save(existingParent);
    }

    @Test
    void updateParent_NonExistingParent_ThrowsParentNotFoundException() {
        ParentDTO parentDTO = new ParentDTO("Jane Doe", "987654321", "password", true, "jane@example.com",
                LocalDate.now(), "456 Avenue", "0987654321", List.of("123456789"));

        // Simula que o Parent não existe
        when(userRepository.findByCpf("987654321")).thenReturn(Optional.empty());

        assertThrows(ParentNotFoundException.class, () -> parentService.updateParent("987654321", parentDTO));
    }

    @Test
    void addStudentToParent_ExistingParentAndStudent_AddsStudent() {
        Parent parent = new Parent();
        parent.setCpf("parentCpf");
        parent.setStudents(new ArrayList<>()); // Initialize students list

        Student student = new Student();
        student.setCpf("studentCpf");

        when(userRepository.findByCpf("parentCpf")).thenReturn(Optional.of(parent));
        when(userRepository.findByCpf("studentCpf")).thenReturn(Optional.of(student));

        parentService.addStudentToParent("parentCpf", "studentCpf");

        assertTrue(parent.getStudents().contains(student));
        verify(userRepository).save(parent);
    }

    @Test
    void addStudentToParent_NonExistingParent_ThrowsParentNotFoundException() {
        when(userRepository.findByCpf("987654321")).thenReturn(Optional.empty());

        assertThrows(ParentNotFoundException.class, () -> parentService.addStudentToParent("987654321", "123456789"));
    }

    @Test
    void addStudentToParent_NonExistingStudent_ThrowsStudentNotFoundException() {
        Parent parent = new Parent();
        parent.setCpf("987654321");

        when(userRepository.findByCpf("987654321")).thenReturn(Optional.of(parent));
        when(userRepository.findByCpf("123456789")).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> parentService.addStudentToParent("987654321", "123456789"));
    }
}