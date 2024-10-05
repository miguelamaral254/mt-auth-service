package br.com.loginauth.services;

import br.com.loginauth.domain.entities.SchoolClass;
import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.dto.SchoolClassDTO;
import br.com.loginauth.dto.StudentResponseDTO;
import br.com.loginauth.exceptions.SchoolClassNotFoundException;
import br.com.loginauth.exceptions.StudentAlreadyExistsException;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.repositories.SchoolClassRepository;
import br.com.loginauth.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolClassService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private StudentRepository studentRepository;

    public SchoolClass createClass(SchoolClassDTO schoolClassDTO) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setLetter(schoolClassDTO.letter());
        schoolClass.setShift(schoolClassDTO.shift());
        schoolClass.setCode(schoolClassDTO.code());
        schoolClass.setTechnicalCourse(schoolClassDTO.technicalCourse());
        schoolClass.setYear(schoolClassDTO.year());
        schoolClass.setDate(schoolClassDTO.date());

        return schoolClassRepository.save(schoolClass);
    }

    public SchoolClass addStudentToClass(Long schoolClassId, String studentCpf) {
        SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId)
                .orElseThrow(() -> new SchoolClassNotFoundException("SchoolClass not found with id " + schoolClassId));

        Student student = (Student) studentRepository.findByCpf(studentCpf)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with CPF " + studentCpf));

        if (schoolClass.getStudents().stream().anyMatch(s -> s.getCpf().equals(studentCpf))) {
            throw new StudentAlreadyExistsException("Student already exists in this class with CPF " + studentCpf);
        }

        schoolClass.getStudents().add(student);
        return schoolClassRepository.save(schoolClass);
    }

    public SchoolClass removeStudentFromClass(Long schoolClassId, String studentCpf) {
        SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId)
                .orElseThrow(() -> new SchoolClassNotFoundException("SchoolClass not found with id " + schoolClassId));

        Student student = (Student) studentRepository.findByCpf(studentCpf)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with CPF " + studentCpf));

        if (schoolClass.getStudents().remove(student)) {
            return schoolClassRepository.save(schoolClass);
        } else {
            throw new StudentNotFoundException("Student not found in this class with CPF " + studentCpf);
        }
    }

    public SchoolClass updateClass(Long id, SchoolClassDTO schoolClassDTO) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new SchoolClassNotFoundException("SchoolClass not found with id " + id));

        schoolClass.setLetter(schoolClassDTO.letter());
        schoolClass.setShift(schoolClassDTO.shift());
        schoolClass.setCode(schoolClassDTO.code());
        schoolClass.setTechnicalCourse(schoolClassDTO.technicalCourse());
        schoolClass.setYear(schoolClassDTO.year());
        schoolClass.setDate(schoolClassDTO.date());

        return schoolClassRepository.save(schoolClass);
    }

    public List<StudentResponseDTO> getStudentsInClass(Long schoolClassId) {
        SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId)
                .orElseThrow(() -> new SchoolClassNotFoundException("SchoolClass not found with id " + schoolClassId));

        return schoolClass.getStudents().stream()
                .map(student -> new StudentResponseDTO(
                        student.getCpf(),
                        student.getName()
                ))
                .collect(Collectors.toList());
    }

    public List<SchoolClass> getAllClasses() {
        return schoolClassRepository.findAll();
    }

    public SchoolClass getClassById(Long id) {
        return schoolClassRepository.findById(id)
                .orElseThrow(() -> new SchoolClassNotFoundException("SchoolClass not found with id " + id));
    }
}
