package br.com.loginauth.repositories;

import br.com.loginauth.domain.entities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByAssessmentsId(Long assessmentsId);
}
