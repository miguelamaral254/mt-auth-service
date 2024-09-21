package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Parent;
import br.com.loginauth.domain.entities.Student;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Role;
import br.com.loginauth.dto.ParentDTO;
import br.com.loginauth.repositories.UserRepository;
import br.com.loginauth.exceptions.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

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

        Optional<User> student = repository.findByCpf(body.studentCpf());
        if (student.isEmpty() || !(student.get() instanceof Student)) {
            throw new IllegalArgumentException("Student does not exist");
        }

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
        newParent.setStudentCPF(body.studentCpf());

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
            parent.setStudentCPF(body.studentCpf());
            repository.save(parent);
        } else {
            throw new IllegalArgumentException("Parent not found");
        }
    }

}
