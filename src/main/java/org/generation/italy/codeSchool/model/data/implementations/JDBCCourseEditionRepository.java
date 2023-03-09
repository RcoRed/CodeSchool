package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.codeSchool.model.entities.Classroom;
import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.codeSchool.model.data.JDBCConstants.*;

public class JDBCCourseEditionRepository implements CourseEditionRepository {
   private Connection con;

   public JDBCCourseEditionRepository(Connection connection) {
      this.con = connection;
   }
   @Override
   public double getTotalCost() {
      return 0;
   }

   @Override
   public Optional<CourseEdition> findMostExpensive() {
      try (Statement st = con.createStatement();
           ResultSet rs = st.executeQuery(FIND_MOST_EXPENSIVE_COURSE_EDITION)){
         if(rs.next()){
            Course maxCourse = databaseToCourse(rs);
            Classroom maxClassroom = databaseToClassroom(rs);
            CourseEdition maxCourseEdition = databaseToCourseEdition(rs, maxCourse, maxClassroom);
            return Optional.of(maxCourseEdition);
         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
      return Optional.empty();
   }

   @Override
   public double findAverageCost() {
      return 0;
   }

   @Override
   public Iterable<Double> findAllDuration() {
      return null;
   }

   @Override
   public Iterable<CourseEdition> findByCourse(long courseId) {
      try (PreparedStatement ps = con.prepareStatement(FIND_COURSE_EDITION_BY_COURSE)){
         ps.setLong(1,courseId);
         try(ResultSet rs = ps.executeQuery()){
            List<CourseEdition> ceList = new ArrayList<>();
            while(rs.next()){
               ceList.add(databaseToCourseEdition(rs,databaseToCourse(rs),databaseToClassroom(rs)));
            }
            return ceList;
         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public Iterable<CourseEdition> findByCourseAndTitleAndPeriod(long courseId, String titlePart, LocalDate startAt, LocalDate endAt) {
      try(PreparedStatement ps = con.prepareStatement(FIND_COURSE_EDITION_BY_COURSE_TITLE_PERIOD)){
         ps.setString(1,titlePart);
         ps.setDate(2,Date.valueOf(startAt));
         ps.setDate(3,Date.valueOf(endAt));
         try(ResultSet rs = ps.executeQuery()){
            List<CourseEdition> ceList = new ArrayList<>();
            while(rs.next()){
               ceList.add(databaseToCourseEdition(rs,databaseToCourse(rs),databaseToClassroom(rs)));
            }
            return ceList;
         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public Iterable<CourseEdition> findMedian() {
      return null;
   }

   @Override
   public Optional<Double> getCourseEditionCostMode() {
      return Optional.empty();
   }

   public Iterable<CourseEdition> dinfByTeacherId(long teacherId){
      try (PreparedStatement ps = con.prepareStatement(FIND_COURSE_EDITION_BY_TEACHER_ID)) {
         ps.setLong(1,teacherId);
         try(ResultSet rs = ps.executeQuery()){
            List<CourseEdition> ceList = new ArrayList<>();
            while(rs.next()){
               ceList.add(databaseToCourseEdition(rs,databaseToCourse(rs),databaseToClassroom(rs)));
            }
            return ceList;
         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }
   private Course databaseToCourse(ResultSet rs) throws SQLException {
      try {
         return new Course(rs.getLong("id_course"),
               rs.getString("title"),
               rs.getString("description"),
               rs.getString("program"),
               rs.getDouble("duration"),
               rs.getBoolean("is_active"),
               rs.getDate("created_at").toLocalDate());
      } catch (SQLException e) {
         throw new SQLException("errore nella lettura dei corsi da database", e);
      }
   }

   private Classroom databaseToClassroom(ResultSet rs) throws SQLException {
      try {
         return new Classroom(rs.getLong("id_classroom"),
               rs.getString("class_name"),
               rs.getInt("max_capacity"),
               rs.getBoolean("is_virtual"),
               rs.getBoolean("is_computerized"),
               rs.getBoolean("has_projector"),
               null);
      } catch (SQLException e) {
         throw new SQLException("errore nella lettura dei corsi da database", e);
      }
   }
   private CourseEdition databaseToCourseEdition(ResultSet rs, Course course, Classroom classroom) throws SQLException {
      try {
         return new CourseEdition(rs.getLong("id_course_edition"),
               course,
               rs.getDate("started_at").toLocalDate(),
               rs.getDouble("price"),
               classroom);
      } catch (SQLException e) {
         throw new SQLException("errore nella lettura dei corsi da database", e);
      }
   }
}
//Esercizio:
//      * Implementare una serie di metodi nella classe JDBCCourseEditionRepository
//      * Tutti i metodi dovranno riportare, oltre alla CourseEdition, anche il relativo Course e la Classroom
//      * 1. findMostExpensive()          +++
//      * 2. findByCourse()               +++
//      * 3. findByCourseTitleAndPeriod() +++
//      * 4. findByTeacherId() -> dammi tutte le CourseEdition tenute dal Teacher con id = x +++