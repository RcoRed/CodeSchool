package org.generation.italy.codeSchool.model.services.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.generation.italy.codeSchool.model.services.abstractions.AbstractDidacticService;

import java.util.List;
import java.util.Optional;

public class StandardDidacticService implements AbstractDidacticService {
    private CourseRepository repo;
    public StandardDidacticService (CourseRepository repo) {
        this.repo = repo; // dependencies injection
    }
    @Override
    public Optional<Course> findCourseById(long id) throws DataException {
        return repo.findById(id);
    }

    @Override
    public List<Course> findCoursesByTitleContains(String part) throws DataException {
        return repo.findByTitleContains(part);
    }

    @Override
    public Course saveCourse(Course course) throws DataException {
        return repo.create(course);
    }

    @Override
    public void updateCourse(Course course) throws EntityNotFoundException, DataException {
        repo.update(course);
    }

    @Override
    public void deleteCourseById(long id) throws EntityNotFoundException, DataException {
        repo.deleteById(id);
    }

    @Override
    public boolean adjustActiveCourse(int numActive) throws DataException {
        // controlla quanti corsi sono attivi nel repo
        // ritorniamo false se il numero di attivi è minore-uguale di numActive
        // altrimenti cancella un numero di corsi dato dalla differenza tra gli attivi e numActive
        // a partire dai più vecchi
        // (solo in InMemoryCourseRepository al momento)
        return false;
    }
}
