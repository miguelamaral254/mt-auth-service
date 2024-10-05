package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.SchoolClass;
import br.com.loginauth.dto.AddStudentRequest;
import br.com.loginauth.dto.SchoolClassDTO;
import br.com.loginauth.dto.StudentResponseDTO;
import br.com.loginauth.exceptions.SchoolClassNotFoundException;
import br.com.loginauth.exceptions.StudentAlreadyExistsException;
import br.com.loginauth.exceptions.StudentNotFoundException;
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

    @PostMapping
    public ResponseEntity<SchoolClass> createSchoolClass(@RequestBody SchoolClassDTO schoolClassDTO) {
        SchoolClass createdSchoolClass = schoolClassService.createClass(schoolClassDTO);
        return new ResponseEntity<>(createdSchoolClass, HttpStatus.CREATED);
    }

    @PostMapping("/addstudent")
    public ResponseEntity<SchoolClass> addStudentToClass(@RequestBody AddStudentRequest request) {
        try {
            SchoolClass updatedSchoolClass = schoolClassService.addStudentToClass(request.classId(), request.cpf());
            return new ResponseEntity<>(updatedSchoolClass, HttpStatus.OK);
        } catch (StudentAlreadyExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (SchoolClassNotFoundException | StudentNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{classId}/students/{studentCpf}")
    public ResponseEntity<Void> removeStudentFromClass(@PathVariable Long classId, @PathVariable String studentCpf) {
        schoolClassService.removeStudentFromClass(classId, studentCpf);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolClass> updateClass(@PathVariable Long id, @RequestBody SchoolClassDTO schoolClassDTO) {
        SchoolClass updatedClass = schoolClassService.updateClass(id, schoolClassDTO);
        return ResponseEntity.ok(updatedClass);
    }

    @GetMapping("/{classId}/students")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsInClass(@PathVariable Long classId) {
        List<StudentResponseDTO> students = schoolClassService.getStudentsInClass(classId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SchoolClass>> getAllClasses() {
        List<SchoolClass> schoolClasses = schoolClassService.getAllClasses();
        return new ResponseEntity<>(schoolClasses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolClass> getClassById(@PathVariable Long id) {
        SchoolClass schoolClass = schoolClassService.getClassById(id);
        return new ResponseEntity<>(schoolClass, HttpStatus.OK);
    }
}
