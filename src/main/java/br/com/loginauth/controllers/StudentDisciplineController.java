package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.StudentDiscipline;
import br.com.loginauth.dto.ResponseStudentDisciplineDTO;
import br.com.loginauth.dto.StudentDisciplineCreateDTO;
import br.com.loginauth.dto.StudentDisciplineDTO;
import br.com.loginauth.services.StudentDisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student-disciplines")
public class StudentDisciplineController {

    private final StudentDisciplineService studentDisciplineService;

    @Autowired
    public StudentDisciplineController(StudentDisciplineService studentDisciplineService) {
        this.studentDisciplineService = studentDisciplineService;
    }

    @PostMapping
    public ResponseEntity<StudentDiscipline> createStudentDiscipline(@RequestBody StudentDisciplineCreateDTO dto) {
        StudentDiscipline createdStudentDiscipline = studentDisciplineService.createStudentDiscipline(dto);
        return ResponseEntity.status(201).body(createdStudentDiscipline);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDisciplineDTO> getStudentDisciplineById(@PathVariable Long id) {
        StudentDisciplineDTO studentDiscipline = studentDisciplineService.getById(id);
        return ResponseEntity.ok(studentDiscipline);
    }

    @GetMapping
    public ResponseEntity<List<StudentDisciplineDTO>> getAllStudentDisciplines() {
        List<StudentDisciplineDTO> studentDisciplines = studentDisciplineService.getAll();
        return ResponseEntity.ok(studentDisciplines);
    }

    @GetMapping("/student/{cpf}/disciplines")
    public ResponseEntity<List<ResponseStudentDisciplineDTO>> getStudentDisciplinesByCpf(@PathVariable String cpf) {
        List<ResponseStudentDisciplineDTO> studentDisciplines = studentDisciplineService.getStudentDisciplinesByStudentCpf(cpf);
        return ResponseEntity.ok(studentDisciplines);
    }


}
