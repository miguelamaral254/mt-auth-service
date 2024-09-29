package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Discipline;
import br.com.loginauth.domain.entities.Grade;
import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.domain.entities.StudentDiscipline;
import br.com.loginauth.dto.GradeDTO;
import br.com.loginauth.exceptions.DisciplineNotFoundException;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.repositories.DisciplineRepository;
import br.com.loginauth.repositories.GradeRepository;
import br.com.loginauth.repositories.StudentDisciplineRepository;
import br.com.loginauth.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private StudentDisciplineRepository studentDisciplineRepository;
    @Autowired
    private StudentDisciplineService studentDisciplineService;

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public Grade getGradeById(Long id) {
        return gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found with id " + id));
    }






    public Grade updateGrade(Long id, Grade gradeDetails) {
        Grade grade = getGradeById(id);
        grade.setAv1(gradeDetails.getAv1());
        grade.setAv2(gradeDetails.getAv2());
        grade.setAv3(gradeDetails.getAv3());
        grade.setAv4(gradeDetails.getAv4());
        return gradeRepository.save(grade);
    }

    public void deleteGrade(Long id) {
        Grade grade = getGradeById(id);
        gradeRepository.delete(grade);
    }
}
