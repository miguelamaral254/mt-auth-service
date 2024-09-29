package br.com.loginauth.repositories;

import br.com.loginauth.domain.entities.StudentDiscipline;
import br.com.loginauth.domain.entities.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentDisciplineRepository extends JpaRepository<StudentDiscipline, Long> {
    List<StudentDiscipline> findByDiscipline(Discipline discipline);
    Optional<StudentDiscipline> findByStudentCpfAndDisciplineId(String studentCpf, Long disciplineId);

    List<StudentDiscipline> findByStudentCpf(String cpf);
}
