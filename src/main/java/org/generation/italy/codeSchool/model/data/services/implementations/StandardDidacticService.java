package org.generation.italy.codeSchool.model.data.services.implementations;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.generation.italy.codeSchool.model.data.services.abstractions.AbstractDidacticService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StandardDidacticService implements AbstractDidacticService {
    //private InMemoryCourseRepository repo;  //ASSOCIAZIONE con un'implementazione
    //private CourseRepository repo = new InMemoryCourseRepository();  //DIPENDENZA con un'implementazione
    private CourseRepository repo; //INIEZIONE delle dipendenze (ok)
    public StandardDidacticService(CourseRepository repo){
        this.repo = repo;  //iniezione delle dipendenze (tecnica) -> inversione del controllo, inversione delle dipendenze
    }
    @Override
    public Optional<Course> findCourseById(long id) throws DataException {
        //Optional<Course> oc = repo.findById(id);
        //return oc;
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
        //chiama al repository per sapere quanti corsi attivi esistono
        //se i corsi attivi sono <= numActive abbiamo finito, ritorniamo false perchè non è stato necessario fare modifiche
        //se i corsi attivi sono > numActive chiameremo un metodo su repository che cancella gli n corsi più vecchi (n parametro input)
        // ritorna poi true nel secondo caso
        int actives = repo.countActiveCourses();
        if (actives <= numActive){
            return false;
        }
        repo.deactivateOldest(actives - numActive);
        return true;
    }
}
