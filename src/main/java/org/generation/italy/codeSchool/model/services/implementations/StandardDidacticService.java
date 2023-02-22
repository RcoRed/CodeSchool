package org.generation.italy.codeSchool.model.services.implementations;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.services.abstractions.AbstractDidacticService;

import java.util.List;
import java.util.Optional;

public class StandardDidacticService implements AbstractDidacticService {

    //private InMemoryCourseRepository repo;  //associazione con un'implementazione (no)
    //private CourseRepository repo = new InMemoryCourseRepository(); //dipendenza con un'implementazione (quasi)
    private final CourseRepository repo; //iniezione delle dipendenze (si)
    public StandardDidacticService(CourseRepository repo){
        this.repo = repo; //iniezione delle dipendenze (tecnica) -> inversione del controllo (design pattern), inversione delle dipendenze ()
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
    public boolean adjustActiveCourses(int numActive) {
        int actives = repo.getActiveCourses().size(); //chiama il repository per scoprire quanti corsi sono attivi
        if (actives <= numActive) return false; //se i corsi attivi sono <= di numActive ritorniamo false (fine)
        int remaining = actives - numActive;
        repo.deleteOldestActiveCourses(remaining); //altrimenti, chiameremo un metodo sul repository che cancella
        return true;                        // gli n corsi più vecchi (n parametro input)
    }
}
