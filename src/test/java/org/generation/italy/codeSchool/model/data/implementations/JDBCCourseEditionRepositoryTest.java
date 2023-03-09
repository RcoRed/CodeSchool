package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.entities.Classroom;
import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import static org.generation.italy.codeSchool.model.data.JDBCConstants.*;
import static org.generation.italy.codeSchool.model.data.implementations.JDBCTestUtils.insertCourse;
import static org.generation.italy.codeSchool.model.data.implementations.JDBCTestUtils.update;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.*;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.CREATED_AT;
import static org.junit.jupiter.api.Assertions.*;

class JDBCCourseEditionRepositoryTest {
   private Course c1;
   private Course c2;
   private Classroom cr1;
   private CourseEdition ce1;
   private CourseEdition ce2;
   private Connection con;
   private JDBCCourseEditionRepository repo;

   @BeforeEach
   void setUp() throws SQLException {
      c1 = new Course(0, TITLE, DESCRIPTION, PROGRAM, DURATION, IS_ACTIVE, CREATED_AT);
      c2 = new Course(0, TITLE2, DESCRIPTION2, PROGRAM2, DURATION2, IS_ACTIVE, CREATED_AT.plusDays(1));
      cr1 = new Classroom(0,CLASSROOM_NAME, CLASSROOM_CAPACITY,CLASSROOM_IS_VIRTUAL,CLASSROOM_IS_COMPUTERIZED,
            CLASSROOM_HAS_PROJECTOR, null);
      ce1 = new CourseEdition(0,c1,COURSE_EDITION_STARTED_AT,COURSE_EDITION_COST,cr1);
      ce2 = new CourseEdition(1,c2,COURSE_EDITION_STARTED_AT,COURSE_EDITION_COST2,cr1);
      con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
      con.setAutoCommit(false);
      int key1 = update(INSERT_COURSE_RETURNING_ID, con, true,c1.getTitle(),
            c1.getDescription(), c1.getProgram(), c1.getDuration(), c1.isActive(),
            Date.valueOf(c1.getCreatedAt()));
      c1.setId(key1);
      int key2 = update(INSERT_COURSE_RETURNING_ID, con, true,c2.getTitle(),
            c2.getDescription(), c2.getProgram(), c2.getDuration(), c2.isActive(),
            Date.valueOf(c2.getCreatedAt()));
      c2.setId(key2);
      int classroomKey = update(INSERT_CLASSROOM_RETURNING_ID, con, true, cr1.getName(),cr1.getMaxCapacity(),
            cr1.isVirtual(), cr1.isComputerized(), cr1.isHasProjector(), null);
      cr1.setId(classroomKey);
      int courseEditionKey1 = update(INSERT_COURSE_EDITION_RETURNING_ID, con,true,c1.getId(),ce1.getStartedAt(),
            ce1.getCost(), cr1.getId());
      ce1.setId(courseEditionKey1);
      int courseEditionKey2 = update(INSERT_COURSE_EDITION_RETURNING_ID, con,true,c2.getId(),ce2.getStartedAt(),
            ce2.getCost(), cr1.getId());
      ce2.setId(courseEditionKey2);
      repo= new JDBCCourseEditionRepository(con);
   }

   @AfterEach
   void tearDown() {
      try {
         con.rollback();
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   @Test
   void getTotalCost() {
   }

   @Test
   void find_most_expensive_should_find_edition_when_present() {
      Optional<CourseEdition> expensive = repo.findMostExpensive();
      assertTrue(expensive.isPresent());
      CourseEdition ce = expensive.get();
      assertEquals(ce2.getId(),ce.getId());
   }

   @Test
   void findAverageCost() {
   }

   @Test
   void findAllDuration() {
   }

   @Test
   void findByCourse() {
   }

   @Test
   void findByCourseTitleAndPeriod() {
   }

   @Test
   void findMedian() {
   }

   @Test
   void getCourseEditionCostMode() {
   }

   @Test
   void findByTeacherId() {

   }
}