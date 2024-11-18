package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.dto.*;
import br.com.loginauth.exceptions.SchoolClassNotFoundException;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.exceptions.UserAlreadyExistsException;
import br.com.loginauth.repositories.StudentRepository;
import br.com.loginauth.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;


    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerStudent(@RequestBody StudentDTO body) {
        try {
            studentService.registerStudent(body);
            return ResponseEntity.ok(new ResponseDTO("Student registered successfully", null));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), null));
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Optional<User>> findStudentByCpf(@PathVariable String cpf) {
        Optional<User> user = studentService.findByCpf(cpf);
        return user.isPresent() ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
    @PutMapping("/update/{cpf}")
    public ResponseEntity<ResponseDTO> updateStudent(@PathVariable String cpf, @RequestBody StudentDTO body) {
        try {
            studentService.updateStudent(cpf, body);
            return ResponseEntity.ok(new ResponseDTO("Student updated successfully", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), null));
        }
    }
    @GetMapping("/{cpf}/lessons")
    public ResponseEntity<List<StudentLessonResponseDTO>> getLessonsByStudentCpf(@PathVariable String cpf) {
        List<StudentLessonResponseDTO> lessons = studentService.getLessonsByStudentCpf(cpf);
        return ResponseEntity.ok(lessons);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllStudents() {
        List<User> students = studentService.findAllStudents();
        return ResponseEntity.ok(students);
    }
    @GetMapping("/{cpf}/school-class")
    public ResponseEntity<List<SchoolClassDTO>> getSchoolClassByStudentCpf(@PathVariable String cpf) {
        try {
            List<SchoolClassDTO> schoolClasses = studentService.getSchoolClassByStudentCPF(cpf);
            return ResponseEntity.ok(schoolClasses);
        } catch (StudentNotFoundException | SchoolClassNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
