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

    //private InMemoryCourseRepository repo;  //associazione con un'implementazione (no)
    //private CourseRepository repo = new InMemoryCourseRepository(); //dipendenza con un'implementazione (quasi)
    private CourseRepository repo; //iniezione delle dipendenze (si)
    public StandardDidacticService(CourseRepository repo){
        this.repo = repo; //iniezione delle dipendenze (tecnica) -> inversione del controllo (design pattern), inversione delle dipendenze (si)
    }


    public Optional<Course> findCourseById(long id) throws DataException {
        Optional<Course> oc = repo.findById(id);
        return oc;
    }

    @Override
    public List<Course> findCoursesByTitleContains(String part) throws DataException {
        List<Course> lc = repo.findByTitleContains(part);
        return lc;
    }

    @Override
    public Course saveCourse(Course course) throws DataException {
        Course c = repo.create(course);
        return c;
    }

    @Override
    public void updateCourse(Course course) throws EntityNotFoundException, DataException {
        repo.update(course);
    }

    @Override
    public void deleteCourseById(long id) throws EntityNotFoundException, DataException {
        repo.deleteById(id);
    }

    //chiama il repository per scoprire quanti corsi sono attivi
    //se i corsi attivi sono <= di numActive ritorniamo false (fine)
    //altrimenti, chiameremo un metodo sul repository che cancella gli n corsi più vecchi (n parametro input)
    public boolean adjustActiveCourses(int numActive) throws DataException {
        int difference = repo.getActiveCourses()-numActive;
        System.out.println(difference);
        if (difference<=0){
            return false;
        }
        repo.deleteNumOldestCourses(difference);
        return true;
    }
}
