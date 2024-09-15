package br.com.loginauth.services;

import br.com.loginauth.domain.entities.SchoolClass;
import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.repositories.SchoolClassRepository;
import br.com.loginauth.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolClassService {

    @Autowired
    private SchoolClassRepository SchoolClassRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SchoolClassRepository schoolClassRepository;

    public SchoolClass createClass(SchoolClass schoolClass) {
        return SchoolClassRepository.save(schoolClass);
    }
    public SchoolClass addStudentToClass(Long schoolClassId, String studentCpf) {
        SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId)
                .orElseThrow(() -> new EntityNotFoundException("SchoolClass not found with id " + schoolClassId));

        Student student = (Student) (Student) studentRepository.findByCpf(studentCpf)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with CPF " + studentCpf));

        schoolClass.getStudents().add(student);
        return schoolClassRepository.save(schoolClass);
    }
    
}
