package org.generation.italy.codeSchool.model.services.abstractions;

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
   boolean adjustActiveCourses(int numActive) throws DataException, EntityNotFoundException; //scoprire quanti corsi attivi ci sono

}
