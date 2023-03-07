package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    List<Course> findAll() throws DataException;

    Optional<Course> findById(long id) throws DataException;

    List<Course> findByTitleContains(String part) throws DataException;

    Course create(Course course) throws DataException;

    void update(Course course) throws EntityNotFoundException,DataException;

    void deleteById(long id) throws EntityNotFoundException,DataException;
    int getActiveCourses() throws DataException;
    boolean adjustActiveCourses(int NumActive) throws DataException;

}

/*
    utitlizziamo le exceptions che abbimo creato noi (nel package "exceptions")
    così da riuscire a poter utilizzare questa interfaccia con tutte le classi che vogliamo
    e gestire l'errore in maniera SPECIFICA!
 */
