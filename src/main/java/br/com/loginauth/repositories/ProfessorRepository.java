package br.com.loginauth.repositories;


import br.com.loginauth.domain.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends UserRepository {
    Optional<User> findByCpf(String cpf);
    List<User> findAll();


}
