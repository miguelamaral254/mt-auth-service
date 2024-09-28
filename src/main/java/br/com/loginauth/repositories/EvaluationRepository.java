package br.com.loginauth.repositories;

import br.com.loginauth.domain.entities.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByGradeId(Long gradeId);
}
