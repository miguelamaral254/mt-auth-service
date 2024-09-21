package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.SchoolClass;
import br.com.loginauth.dto.AddStudentRequest;
import br.com.loginauth.dto.SchoolClassDTO;
import br.com.loginauth.dto.StudentResponseDTO;
import br.com.loginauth.services.SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolclasses")
public class SchoolClassController {

    @Autowired
    private SchoolClassService schoolClassService;

    // Criar uma nova turma
    @PostMapping
    public ResponseEntity<SchoolClass> createSchoolClass(@RequestBody SchoolClassDTO schoolClassDTO) {

        SchoolClass createdSchoolClass = schoolClassService.createClass(schoolClassDTO);
        return new ResponseEntity<>(createdSchoolClass, HttpStatus.CREATED);
    }

    // Adicionar um estudante a uma turma
    @PostMapping("/addstudent")
    public ResponseEntity<SchoolClass> addStudentToClass(@RequestBody AddStudentRequest request) {
        SchoolClass updatedSchoolClass = schoolClassService.addStudentToClass(request.getClassId(), request.getCpf());
        return new ResponseEntity<>(updatedSchoolClass, HttpStatus.OK);
    }





    // Endpoint para obter os alunos de uma turma específica
    @GetMapping("/{classId}/students")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsInClass(@PathVariable Long classId) {
        List<StudentResponseDTO> students = schoolClassService.getStudentsInClass(classId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Buscar todas as turmas
    @GetMapping
    public ResponseEntity<List<SchoolClass>> getAllClasses() {
        List<SchoolClass> schoolClasses = schoolClassService.getAllClasses();
        return new ResponseEntity<>(schoolClasses, HttpStatus.OK);
    }

    // Buscar uma turma por ID
    @GetMapping("/{id}")
    public ResponseEntity<SchoolClass> getClassById(@PathVariable Long id) {
        SchoolClass schoolClass = schoolClassService.getClassById(id);
        return new ResponseEntity<>(schoolClass, HttpStatus.OK);
    }
}
