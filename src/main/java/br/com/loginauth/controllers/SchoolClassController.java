package br.com.loginauth.controllers;

import br.com.loginauth.domain.entities.SchoolClass;
import br.com.loginauth.services.SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes")
public class SchoolClassController {

    @Autowired
    private SchoolClassService SchoolClassService;

    @PostMapping
    public ResponseEntity<SchoolClass> createSchoolClass(@RequestBody SchoolClass schoolClass) {
        SchoolClass createdSchoolClass = SchoolClassService.createClass(schoolClass);
        return new ResponseEntity<SchoolClass>(createdSchoolClass, HttpStatus.CREATED);
    }
}
