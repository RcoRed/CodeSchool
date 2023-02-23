package org.generation.italy.codeSchool.model.data.services.abstractions;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface AbstractDidacticService {
    Optional<Course> findCourseById(long id) throws DataException;

    List<Course> findCoursesByTitleContains(String part) throws DataException;

    Course saveCourse(Course course) throws DataException;

    void updateCourse(Course course) throws EntityNotFoundException,DataException;

    void deleteCourseById(long id) throws EntityNotFoundException,DataException;
    boolean adjustActiveCourses(int numActive) throws DataException; //gli passo tipo 10 e deve controllare che ci siano al massimo 10 corsi attivi
                                                                    // se i corsi sono più di 10 mantengo i 10 più nuovi
}
