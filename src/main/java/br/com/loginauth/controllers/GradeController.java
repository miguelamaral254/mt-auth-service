package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.Grade;
import br.com.loginauth.dto.GradeCreateDTO;
import br.com.loginauth.dto.GradeResponseDTO;
import br.com.loginauth.services.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class GradeController {

    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    // Endpoint para criar uma nova Grade associada a um StudentDiscipline
    @PostMapping
    public GradeResponseDTO createGrade(@RequestBody GradeCreateDTO dto) {
        return gradeService.createGrade(dto);
    }
    @GetMapping
    public List<GradeResponseDTO> getAllGrades() {
        return gradeService.GetAllGrades();
    }

    @GetMapping("/student/{cpf}")
    public List<GradeResponseDTO> getGradesByStudentCpf(@PathVariable String cpf) {
        return gradeService.getGradesByStudentCpf(cpf);
    }
    @GetMapping("/student/{cpf}/discipline/{disciplineId}")
    public GradeResponseDTO getGradeByStudentCpfAndDisciplineId(
            @PathVariable String cpf,
            @PathVariable Long disciplineId) {
        return gradeService.getGradeByStudentCpfAndDisciplineId(cpf, disciplineId);
    }
}
