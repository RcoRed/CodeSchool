package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

import static org.generation.italy.codeSchool.model.data.Constants.CSV_COURSE;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.generation.italy.codeSchool.model.data.Constants.*;
class InMemoryCourseRepositoryTest {
   private static final String CSVLINE1=String.format(Locale.US,CSV_COURSE, ID1,TITLE,DESCRIPTION,PROGRAM,DURATION,IS_ACTIVE,LocalDate.of(1235,12,8));
   private static final String CSVLINE2=String.format(Locale.US,CSV_COURSE,ID2,TITLE+TEST,DESCRIPTION+TEST,
         PROGRAM+TEST,DURATION+1,IS_ACTIVE, LocalDate.of(2005,12,8));
   private static final String CSVLINE3=String.format(Locale.US,CSV_COURSE,ID3,TITLE+TEST,DESCRIPTION+TEST,
         PROGRAM+TEST,DURATION+2,IS_ACTIVE,LocalDate.of(135,12,8));
   InMemoryCourseRepository repo = new InMemoryCourseRepository();


   private Course CSVToCourse(String CSVLine){
      String[] tokens = CSVLine.split(",");
      return new Course(Long.parseLong(tokens[0]), tokens[1], tokens[2],
            tokens[3], Double.parseDouble(tokens[4]), Boolean.parseBoolean(tokens[5]), LocalDate.parse(tokens[6]));

   }
   @org.junit.jupiter.api.BeforeEach
   void setUp() throws FileNotFoundException {
      Course COURSE1 =CSVToCourse(CSVLINE1);
      Course COURSE2 =CSVToCourse(CSVLINE2);
      Course COURSE3 =CSVToCourse(CSVLINE3);
      COURSE2.setActive(false);

      repo.create(COURSE1);
      repo.create(COURSE2);
      repo.create(COURSE3);
   }
   @Test
   void countActivesCourses_should_return_num_of_actives_courses() {
      int active = repo.countActivesCourses();
      assertEquals(2,active);
   }

   @Test
   void deleteOldCourses() {
      try {
         repo.desactiveOldCourses(1);
      } catch (EntityNotFoundException e) {
         new EntityNotFoundException(ENTITY_NOT_FOUND + e.getMessage());
      }
//      System.out.println(repo.countActivesCourses());
      assertEquals(1,repo.countActivesCourses());
   }
}