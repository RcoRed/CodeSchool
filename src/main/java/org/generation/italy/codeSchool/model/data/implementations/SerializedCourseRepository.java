package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class SerializedCourseRepository implements CourseRepository {
   @Override
   public Optional<Course> findById(long id) throws DataException {
      return Optional.empty();
   }

   @Override
   public List<Course> findByTitleContains(String part) throws DataException {
      return null;
   }

   @Override
   public Course create(Course course) throws DataException {
      return null;
   }

   @Override
   public void update(Course course) throws EntityNotFoundException, DataException {

   }

   @Override
   public void deleteById(long id) throws EntityNotFoundException, DataException {

   }
}
