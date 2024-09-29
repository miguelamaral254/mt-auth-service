package br.com.loginauth.controllers;


import br.com.loginauth.domain.entities.StudentDiscipline;
import br.com.loginauth.dto.StudentDisciplineDTO;
import br.com.loginauth.services.StudentDisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student-disciplines")
public class StudentDisciplineController {

    @Autowired
    private StudentDisciplineService studentDisciplineService;
/*
    @PostMapping
    public StudentDiscipline createStudentDiscipline(@RequestBody StudentDisciplineDTO dto) {
        return studentDisciplineService.createStudentDiscipline(dto);
    }
}

 */
}