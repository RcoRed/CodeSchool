package org.generation.italy.codeSchool.model.services.implementations;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.services.abstractions.AbstractDidacticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StandardDidacticService implements AbstractDidacticService {
    private CourseRepository repo;
    @Autowired
    public StandardDidacticService(CourseRepository repo){
        this.repo = repo;       //iniezione delle dipendenze (tecnica) -> inversione del controllo (design pattern) o inversione delle dipendenze
        System.out.println(this.repo.getClass().getName());
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
        //se i corsi attivi sono <= numActive ritorniamo false per segnalare
        //che non è stato necessario apportare alcuna modifica
        //altrimenti chiameremo un metodo sul repository che cancella gli
        //n corsi più vecchi
        int actives = repo.countActiveCourses();
        if (actives <= numActive){
            return false;
        }
        repo.deactivateOldest(actives - numActive);
        return true;
    }
}
