package br.com.loginauth.services;

import br.com.loginauth.domain.entities.SchoolClass;
import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.dto.StudentResponseDTO;
import br.com.loginauth.repositories.SchoolClassRepository;
import br.com.loginauth.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public SchoolClass createClass(SchoolClass schoolClass) {
        return schoolClassRepository.save(schoolClass);
    }

    public SchoolClass addStudentToClass(Long schoolClassId, String studentCpf) {
        // Busca a classe com base no ID
        SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId)
                .orElseThrow(() -> new EntityNotFoundException("SchoolClass not found with id " + schoolClassId));

        // Busca o aluno com base no CPF
        Student student = (Student) studentRepository.findByCpf(studentCpf)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with CPF " + studentCpf));

        // Adiciona o aluno à turma
        schoolClass.getStudents().add(student);

        // Salva a turma atualizada
        return schoolClassRepository.save(schoolClass);
    }
    // Busca aluno dentro de uma turma
    public List<StudentResponseDTO> getStudentsInClass(Long schoolClassId) {
        SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId)
                .orElseThrow(() -> new EntityNotFoundException("SchoolClass not found with id " + schoolClassId));

        return schoolClass.getStudents().stream()
                .map(student -> {
                    StudentResponseDTO dto = new StudentResponseDTO();
                    dto.setCpf(student.getCpf());
                    dto.setName(student.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    // Buscar todas as turmas
    public List<SchoolClass> getAllClasses() {
        return schoolClassRepository.findAll();
    }

    // Buscar uma turma por ID
    public SchoolClass getClassById(Long id) {
        return schoolClassRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SchoolClass not found with id " + id));
    }
}
