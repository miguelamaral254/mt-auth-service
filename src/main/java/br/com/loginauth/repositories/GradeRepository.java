package br.com.loginauth.repositories;

import br.com.loginauth.domain.entities.Grade;
import br.com.loginauth.domain.enums.EvaluationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findByStudentCpfAndDisciplineIdAndEvaluationType(String studentCpf, Long disciplineId, EvaluationType evaluationType);
}
