package br.com.loginauth.controllers;

import br.com.loginauth.dto.LessonDTO;

import br.com.loginauth.exceptions.DisciplineNotFoundException;
import br.com.loginauth.exceptions.LessonNotFoundException;
import br.com.loginauth.exceptions.ProfessorNotFoundException;
import br.com.loginauth.exceptions.SchoolClassNotFoundException;
import br.com.loginauth.services.LessonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public ResponseEntity<LessonDTO> createLesson(@RequestBody LessonDTO lessonDTO) {
        try {
            LessonDTO createdLesson = lessonService.createLesson(lessonDTO);
            return ResponseEntity.ok(createdLesson);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long id) {
        try {
            LessonDTO lessonDTO = lessonService.getLessonById(id);
            return ResponseEntity.ok(lessonDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<LessonDTO>> getAllLessons() {
        List<LessonDTO> lessonDTOs = lessonService.getAllLessons();
        return ResponseEntity.ok(lessonDTOs);
    }
    @GetMapping("name/{name}")
    public ResponseEntity<LessonDTO> getLessonByName(@PathVariable String name) {
        try {
            LessonDTO lessonDTO = lessonService.getLessonByName(name);
            return ResponseEntity.ok(lessonDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable Long id, @RequestBody LessonDTO lessonDTO) {
        try {
            LessonDTO updatedLesson = lessonService.updateLesson(id, lessonDTO);
            return ResponseEntity.ok(updatedLesson);
        } catch (LessonNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DisciplineNotFoundException | ProfessorNotFoundException | SchoolClassNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/student/{cpf}/class/{schoolClassId}")
    public ResponseEntity<List<LessonDTO>> getLessonsByStudentAndClass(
            @PathVariable String cpf,
            @PathVariable Long schoolClassId) {
        try {
            List<LessonDTO> lessons = lessonService.getLessonsByStudentAndClass(cpf, schoolClassId);
            return ResponseEntity.ok(lessons);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
