package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends AbstractCrudRepository<Course>{
    List<Course> findByTitleContains(String part) throws DataException;
    int countActiveCourses() throws DataException;
    void deactivateOldest(int n) throws DataException;
    boolean adjustActiveCourses(int NumActive) throws DataException;
}
