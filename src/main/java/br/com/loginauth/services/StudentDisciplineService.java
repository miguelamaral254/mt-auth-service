package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Discipline;
import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.domain.entities.StudentDiscipline;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.dto.ResponseStudentDisciplineDTO;
import br.com.loginauth.dto.StudentDisciplineCreateDTO;
import br.com.loginauth.dto.StudentDisciplineDTO;
import br.com.loginauth.exceptions.StudentDisciplineNotFoundException;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.repositories.DisciplineRepository;
import br.com.loginauth.repositories.StudentDisciplineRepository;
import br.com.loginauth.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentDisciplineService {

    private final StudentRepository studentRepository;
    private final DisciplineRepository disciplineRepository;
    private final StudentDisciplineRepository studentDisciplineRepository;

    @Autowired
    public StudentDisciplineService(StudentRepository studentRepository, DisciplineRepository disciplineRepository, StudentDisciplineRepository studentDisciplineRepository) {
        this.studentRepository = studentRepository;
        this.disciplineRepository = disciplineRepository;
        this.studentDisciplineRepository = studentDisciplineRepository;
    }


    public StudentDiscipline createStudentDiscipline(StudentDisciplineCreateDTO dto) {
        Optional<User> studentOpt = studentRepository.findByCpf(dto.studentCpf());
        Optional<Discipline> disciplineOpt = disciplineRepository.findById(dto.disciplineId());

        if (studentOpt.isEmpty()) {
            throw new StudentNotFoundException("Student not found with CPF: " + dto.studentCpf());
        }

        if (disciplineOpt.isEmpty()) {
            throw new RuntimeException("Discipline not found with ID: " + dto.disciplineId());
        }

        Student student = (Student) studentOpt.get();
        Discipline discipline = disciplineOpt.get();

        StudentDiscipline studentDiscipline = new StudentDiscipline();
        studentDiscipline.setStudent(student);
        studentDiscipline.setDiscipline(discipline);

        return studentDisciplineRepository.save(studentDiscipline);
    }

    public StudentDisciplineDTO getById(Long id) {
        StudentDiscipline studentDiscipline = studentDisciplineRepository.findById(id)
                .orElseThrow(() -> new StudentDisciplineNotFoundException("StudentDiscipline not found with id " + id));
        return mapToStudentDisciplineDTO(studentDiscipline);
    }

    public List<StudentDisciplineDTO> getAll() {
        List<StudentDiscipline> studentDisciplines = studentDisciplineRepository.findAll();
        return studentDisciplines.stream()
                .map(this::mapToStudentDisciplineDTO)
                .collect(Collectors.toList());
    }

    // Método para buscar disciplinas de um aluno pelo CPF, utilizando ResponseStudentDisciplineDTO
    public List<ResponseStudentDisciplineDTO> getStudentDisciplinesByStudentCpf(String cpf) {
        if (!studentRepository.existsByCpf(cpf)) {
            throw new StudentNotFoundException("Student not found with CPF: " + cpf);
        }

        List<StudentDiscipline> studentDisciplines = studentDisciplineRepository.findByStudentCpf(cpf);
        return studentDisciplines.stream()
                .map(this::mapToResponseStudentDisciplineDTO) // Mapeamento específico para ResponseStudentDisciplineDTO
                .collect(Collectors.toList());
    }

    // Método de mapeamento específico para ResponseStudentDisciplineDTO
    private ResponseStudentDisciplineDTO mapToResponseStudentDisciplineDTO(StudentDiscipline studentDiscipline) {
        return new ResponseStudentDisciplineDTO(
                studentDiscipline.getId(), // ID do relacionamento entre estudante e disciplina
                studentDiscipline.getStudent().getCpf(), // CPF do estudante
                studentDiscipline.getDiscipline().getId() // ID da disciplina
        );
    }

    // Método de mapeamento específico para StudentDisciplineDTO
    private StudentDisciplineDTO mapToStudentDisciplineDTO(StudentDiscipline studentDiscipline) {
        return new StudentDisciplineDTO(
                studentDiscipline.getStudent().getCpf(), // CPF do estudante
                studentDiscipline.getId(), // ID do relacionamento
                studentDiscipline.getDiscipline().getId() // ID da disciplina
        );
    }
}
