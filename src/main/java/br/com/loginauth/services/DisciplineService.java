package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Discipline;
import br.com.loginauth.repositories.DisciplineRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DisciplineService {

    @Autowired
    private DisciplineRepository disciplineRepository;

    public Discipline createDiscipline(Discipline discipline) {
        return disciplineRepository.save(discipline);
    }

    public Discipline getDisciplineById(Long id) {
        return disciplineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discipline not found with id " + id));
    }

    public Discipline updateDiscipline(Long id, Discipline updatedDiscipline) {
        Discipline existingDiscipline = getDisciplineById(id);
        existingDiscipline.setName(updatedDiscipline.getName());
        return disciplineRepository.save(existingDiscipline);
    }

    public void deleteDiscipline(Long id) {
        disciplineRepository.deleteById(id);
    }
}
