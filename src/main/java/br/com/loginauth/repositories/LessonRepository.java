package br.com.loginauth.repositories;

import br.com.loginauth.domain.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    // Método para encontrar uma lição pelo nome
    Optional<Lesson> findByName(String name);
}
