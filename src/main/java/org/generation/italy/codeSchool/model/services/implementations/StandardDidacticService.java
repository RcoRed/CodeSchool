package org.generation.italy.codeSchool.model.services.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.services.abstractions.AbstractDidacticService;

import java.util.List;
import java.util.Optional;

public class StandardDidacticService implements AbstractDidacticService {
    @Override
    public Optional<Course> findCourseById(long id) throws DataException {
        return Optional.empty();
    }

    @Override
    public List<Course> findCoursesByTitleContains(String part) throws DataException {
        return null;
    }

    @Override
    public Course saveCourse(Course course) throws DataException {
        return null;
    }

    @Override
    public void updateCourse(Course course) throws EntityNotFoundException, DataException {

    }

    @Override
    public void deleteCourseById(long id) throws EntityNotFoundException, DataException {

    }
}
