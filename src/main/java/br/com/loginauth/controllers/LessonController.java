package br.com.loginauth.controllers;

import br.com.loginauth.dto.LessonDTO;

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
            return ResponseEntity.notFound().build(); // Handle the exception as needed
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
            return ResponseEntity.notFound().build(); // Retorno de erro 404
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Captura de exceções gerais
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable Long id, @RequestBody LessonDTO lessonDTO) {
        try {
            LessonDTO updatedLesson = lessonService.updateLesson(id, lessonDTO);
            return ResponseEntity.ok(updatedLesson);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); 
        }
    }

}
