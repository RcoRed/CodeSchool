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

   CourseRepository repo  = new InMemoryCourseRepository();
   public StandardDidacticService (CourseRepository repo){ //può ricevere qualsiesi implementazione dell'interfaccia repository
      this.repo = repo;                                    //iniezionde delle dipendenze -> inversione del controllo, inversione delle dipendenze
   }
   @Override
   public Optional<Course> findCourseById(long id) throws DataException {
      return repo.findById(id);
   }

   @Override
   public List<Course> findCoursesByTitleContains(String part) throws DataException {
      return findCoursesByTitleContains(part);
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
      return false;
   }
}
