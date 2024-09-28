package br.com.loginauth.repositories;

import br.com.loginauth.domain.entities.Assessments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentsRepository extends JpaRepository<Assessments, Long> {

    List<Assessments> findByStudentCpf(String studentCpf);


    List<Assessments> findByDisciplineId(Long disciplineId);
}
