package br.com.loginauth.services;
import br.com.loginauth.domain.entities.Parent;
import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Role;
import br.com.loginauth.dto.ParentDTO;
import br.com.loginauth.exceptions.ParentNotFoundException;
import br.com.loginauth.exceptions.StudentNotFoundException;
import br.com.loginauth.repositories.UserRepository;
import br.com.loginauth.exceptions.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void registerParent(ParentDTO body) {
        Optional<User> user = repository.findByCpf(body.cpf());

        if (user.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        List<Student> students = body.studentCpfs().stream()
                .map(cpf -> {
                    Optional<User> studentUser = repository.findByCpf(cpf);
                    if (studentUser.isEmpty() || !(studentUser.get() instanceof Student)) {
                        throw new StudentNotFoundException("Student with CPF " + cpf + " does not exist");
                    }
                    return (Student) studentUser.get();
                })
                .collect(Collectors.toList());

        Parent newParent = new Parent();
        newParent.setCpf(body.cpf());
        newParent.setPassword(passwordEncoder.encode(body.password()));
        newParent.setName(body.name());
        newParent.setEmail(body.email());
        newParent.setRole(Role.PARENT);
        newParent.setActive(body.active());
        newParent.setCreateDate(LocalDateTime.now());
        newParent.setBirthDate(Date.valueOf(body.birthDate()));
        newParent.setAddress(body.address());
        newParent.setPhone(body.phone());
        newParent.setStudents(students);

        repository.save(newParent);
    }

    public Optional<User> findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    public void updateParent(String cpf, ParentDTO body) {
        Optional<User> existingUser = repository.findByCpf(cpf);
        if (existingUser.isPresent() && existingUser.get() instanceof Parent) {
            Parent parent = (Parent) existingUser.get();
            parent.setName(body.name());
            parent.setEmail(body.email());
            parent.setActive(body.active());
            parent.setBirthDate(Date.valueOf(body.birthDate()));
            parent.setAddress(body.address());
            parent.setPhone(body.phone());

            List<Student> students = body.studentCpfs().stream()
                    .map(studentCpf -> {
                        Optional<User> studentUser = repository.findByCpf(studentCpf);
                        if (studentUser.isEmpty() || !(studentUser.get() instanceof Student)) {
                            throw new StudentNotFoundException("Student with CPF " + studentCpf + " does not exist");
                        }
                        return (Student) studentUser.get();
                    })
                    .collect(Collectors.toList());

            parent.setStudents(students);

            repository.save(parent);
        } else {
            throw new ParentNotFoundException("Parent not found");
        }
    }
    public void addStudentToParent(String parentCpf, String studentCpf) {
        Parent parent = repository.findByCpf(parentCpf)
                .filter(Parent.class::isInstance)
                .map(Parent.class::cast)
                .orElseThrow(() -> new ParentNotFoundException("Parent not found"));

        Student student = repository.findByCpf(studentCpf)
                .filter(Student.class::isInstance)
                .map(Student.class::cast)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        parent.getStudents().add(student);
        repository.save(parent);
    }
    public List<User> findAllParents() {
        return repository.findAll().stream()
                .filter(user -> user instanceof Parent)
                .collect(Collectors.toList());
    }
}