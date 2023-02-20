package org.generation.italy.codeSchool.model.services.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.generation.italy.codeSchool.model.services.abstractions.AbstractDidacticService;

import java.util.List;
import java.util.Optional;

public class StandarDidacticService implements AbstractDidacticService {

    //private InMemoryCourseRepository repo;  //associazione con un'implementazione (no)
    //private CourseRepository repo;          //dipendenza con un'implementazione (quasi)
    private CourseRepository repo;
    public StandarDidacticService(CourseRepository repo) {
        this.repo = repo;       //iniezione delle dipendenze (tecnica) -> inversione del controllo (design pattern), iversione delle dipendenze (si)
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
    public boolean adjustActiveCourses(int numActive) throws DataException {
        //chiama il repository per scoprire quanti corsi attivi esistono
        //se i corsi attivi sono <= di numActive allora ritorna false
        //altrimenti, chiama un metodo sul repository che cancella gli n corsi più vecchi dove n sarà un parametro in input
        return false;
    }
}
