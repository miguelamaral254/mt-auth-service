package br.com.loginauth.repositories;

import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Role;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByCpf(String cpf);
    List<User> findByRole(Role role);

}
