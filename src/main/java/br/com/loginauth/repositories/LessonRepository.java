package br.com.loginauth.repositories;

import br.com.loginauth.domain.entities.Lesson;
import br.com.loginauth.domain.enums.Schedule;
import br.com.loginauth.domain.enums.Week;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Optional<Lesson> findByWeekDayAndStartTimeAndSchoolClassId(Week weekDay, Schedule startTime, Long schoolClassId);

    Optional<Lesson> findByWeekDayAndStartTimeAndProfessorCpf(Week weekDay, Schedule startTime, String professorCpf);

    Optional<Lesson> findByName(String name);
}
