package br.com.loginauth.repositories;

import br.com.loginauth.domain.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends UserRepository {
    Optional<User> findByCpf(String cpf);

}
