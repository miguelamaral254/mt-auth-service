package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Coordination;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Role;
import br.com.loginauth.dto.CoordinationDTO;
import br.com.loginauth.repositories.CoordinationRepository;
import br.com.loginauth.repositories.UserRepository;
import br.com.loginauth.exceptions.UserAlreadyExistsException;
import br.com.loginauth.exceptions.CoordinationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoordinationService {

    private final CoordinationRepository coordinationRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void registerCoordination(CoordinationDTO body) {
        Optional<User> user = userRepository.findByCpf(body.cpf());

        if (user.isEmpty()) {
            Coordination newCoordination = new Coordination();
            newCoordination.setCpf(body.cpf());
            newCoordination.setPassword(passwordEncoder.encode(body.password()));
            newCoordination.setName(body.name());
            newCoordination.setEmail(body.email());
            newCoordination.setRole(Role.ADMIN);
            newCoordination.setActive(body.active());
            newCoordination.setCreateDate(LocalDateTime.now());
            newCoordination.setBirthDate(Date.valueOf(body.birthDate()));
            newCoordination.setAddress(body.address());
            newCoordination.setPhone(body.phone());
            newCoordination.setRegistration(body.registration());

            coordinationRepository.save(newCoordination);
        } else {
            throw new UserAlreadyExistsException("User already exists");
        }
    }

    public Optional<User> findByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

    public void updateCoordination(String cpf, CoordinationDTO body) {
        Optional<User> existingUser = userRepository.findByCpf(cpf);

        if (existingUser.isPresent() && existingUser.get() instanceof Coordination) {
            Coordination coordination = (Coordination) existingUser.get();

            coordination.setName(body.name());
            coordination.setEmail(body.email());
            coordination.setActive(body.active());
            coordination.setBirthDate(Date.valueOf(body.birthDate()));
            coordination.setAddress(body.address());
            coordination.setPhone(body.phone());
            coordination.setRegistration(body.registration());

            coordinationRepository.save(coordination);
        } else {
            throw new CoordinationNotFoundException("Coordination not found for CPF: " + cpf);
        }
    }

}
