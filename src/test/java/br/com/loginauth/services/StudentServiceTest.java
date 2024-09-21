package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Role;
import br.com.loginauth.dto.StudentDTO;
import br.com.loginauth.exceptions.UserAlreadyExistsException;
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

class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerStudent_UserDoesNotExist_SavesStudent() {
        StudentDTO studentDTO = new StudentDTO("John Doe", "123456789", "password", true, "john@example.com",
                LocalDateTime.now().toLocalDate(), "123 Street", "1234567890", "REG123");

        when(userRepository.findByCpf(studentDTO.cpf())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(studentDTO.password())).thenReturn("encodedPassword");

        studentService.registerStudent(studentDTO);

        verify(userRepository).save(any(Student.class));
    }

    @Test
    void registerStudent_UserExists_ThrowsUserAlreadyExistsException() {
        StudentDTO studentDTO = new StudentDTO("John Doe", "123456789", "password", true, "john@example.com",
                LocalDateTime.now().toLocalDate(), "123 Street", "1234567890", "REG123");

        when(userRepository.findByCpf(studentDTO.cpf())).thenReturn(Optional.of(new Student()));

        assertThrows(UserAlreadyExistsException.class, () -> studentService.registerStudent(studentDTO));
    }

    @Test
    void updateStudent_ExistingStudent_UpdatesStudent() {
        StudentDTO studentDTO = new StudentDTO("John Doe", "123456789", "newPassword", true, "john@example.com",
                LocalDateTime.now().toLocalDate(), "456 Avenue", "0987654321", "REG456");

        Student existingStudent = new Student();
        existingStudent.setCpf("123456789");
        existingStudent.setName("Old Name");

        when(userRepository.findByCpf(studentDTO.cpf())).thenReturn(Optional.of(existingStudent));

        studentService.updateStudent("123456789", studentDTO);

        assertEquals("John Doe", existingStudent.getName());
        assertEquals("456 Avenue", existingStudent.getAddress());
        verify(userRepository).save(existingStudent);
    }

    @Test
    void updateStudent_NonExistingStudent_ThrowsIllegalArgumentException() {
        StudentDTO studentDTO = new StudentDTO("John Doe", "123456789", "password", true, "john@example.com",
                LocalDateTime.now().toLocalDate(), "123 Street", "1234567890", "REG123");

        when(userRepository.findByCpf(studentDTO.cpf())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> studentService.updateStudent("123456789", studentDTO));
    }
}
