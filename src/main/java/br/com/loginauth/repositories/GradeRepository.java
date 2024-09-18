package br.com.loginauth.repositories;

import br.com.loginauth.domain.entities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    // Retorna todas as notas de um aluno específico
    List<Grade> findByStudentCpf(String studentCpf);

    // Retorna todas as notas de uma disciplina específica
    List<Grade> findByDisciplineId(Long disciplineId);
}
