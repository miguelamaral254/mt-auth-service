package br.com.loginauth.repositories;

import br.com.loginauth.domain.entities.Coordination;
import br.com.loginauth.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoordinationRepository extends UserRepository {
    Optional<User> findByCpf(String cpf);
}
