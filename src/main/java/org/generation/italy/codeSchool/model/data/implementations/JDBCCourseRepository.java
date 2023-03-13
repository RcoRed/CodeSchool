package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.entities.Course;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.codeSchool.model.data.JDBCConstants.*;


@Repository
@Profile("jdbc")
public class JDBCCourseRepository implements CourseRepository {
    /*public static int askToClient;
    static{
        System.out.println("inizializzazione statica");
        Driver d=new Driver();
        try {
            DriverManager.registerDriver(d);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

   private Connection con;

   public JDBCCourseRepository(Connection connection) {
      this.con = connection;
   }


   @Override
   public List<Course> findAll() throws DataException {
      try (
            Statement st = con.createStatement();//factory method pattern
            ResultSet rs = st.executeQuery(COURSE_QUERY)
      ) {
         //Class.forName("org.postgresql.Driver");   //hack
         List<Course> courseList = new ArrayList<>();
         while (rs.next()) {
            courseList.add(databaseToCourse(rs));
         }
         return courseList;
            /*for(Course course: courseList){
                System.out.println(course.getTitle());
            }*/
         //courseList.forEach(c-> System.out.println(c.getTitle()));
         //courseList.forEach(System.out::println);
            /*courseList.stream().map(Course::getTitle)
                               .forEach(System.out::println);*/
      } catch (SQLException e) {
         e.printStackTrace();
         throw new DataException("errore nella lettura dei corsi da database", e);
      }

   }

   @Override
   public Optional<Course> findById(long id) throws DataException {
      try (
            PreparedStatement st = con.prepareStatement(FIND_COURSE_BY_ID);//factory method pattern
      ) {
         st.setLong(1, id);
         try (ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
               return Optional.of(databaseToCourse(rs));
            }
            return Optional.empty();
         }
      } catch (SQLException e) {
         e.printStackTrace();
         throw new DataException("errore nella lettura dei corsi da database", e);
      }
   }

   @Override
   public List<Course> findByTitleContains(String part) throws DataException {
      try (
            PreparedStatement st = con.prepareStatement(FIND_BY_TITLE_CONTAINS);//factory method pattern
      ) {
         st.setString(1, "%" + part + "%");
         try (ResultSet rs = st.executeQuery()) {
            List<Course> courseList = new ArrayList<>();
            while (rs.next()) {
               courseList.add(databaseToCourse(rs));
            }
            return courseList;
         }

      } catch (SQLException e) {
         e.printStackTrace();
         throw new DataException("errore nella lettura dei corsi da database", e);
      }
   }

   @Override
   public Course create(Course course) throws DataException {
      try (
            PreparedStatement st = con.prepareStatement(INSERT_COURSE);//factory method pattern
            Statement st2 = con.createStatement();//factory method pattern
            ResultSet rs = st2.executeQuery(NEXT_VAL_COURSE);
      ) {
         rs.next();
         long nextVal = rs.getLong("nextval");
         course.setId(nextVal);
         st.setLong(1, course.getId());
         st.setString(2, course.getTitle());
         st.setString(3, course.getDescription());
         st.setString(4, course.getProgram());
         st.setDouble(5, course.getDuration());
         st.setBoolean(6, course.isActive());
         st.setDate(7, Date.valueOf(course.getCreatedAt()));
         st.executeUpdate();
         return course;
      } catch (SQLException e) {
         e.printStackTrace();
         throw new DataException("errore nell'insermiento del corso", e);
      }

   }
   public Course create2(Course course) throws DataException {
      try (
            PreparedStatement st = con.prepareStatement(INSERT_COURSE_RETURNING_ID,Statement.RETURN_GENERATED_KEYS);//factory method pattern
      ) {
         st.setString(1, course.getTitle());
         st.setString(2, course.getDescription());
         st.setString(3, course.getProgram());
         st.setDouble(4, course.getDuration());
         st.setBoolean(5, course.isActive());
         st.setDate(6, Date.valueOf(course.getCreatedAt()));
         st.executeUpdate();
         try (ResultSet keys = st.getGeneratedKeys()){
            keys.next();
            long key = keys.getLong(1);
            course.setId(key);
            return course;
         }
      } catch (SQLException e) {
         e.printStackTrace();
         throw new DataException("errore nell'insermiento del corso", e);
      }

   }

   @Override
   public void update(Course course) throws EntityNotFoundException, DataException {
      try (PreparedStatement st = con.prepareStatement(UPDATE_COURSE)){
         st.setString(1, course.getTitle());
         st.setString(2, course.getDescription());
         st.setString(3, course.getProgram());
         st.setDouble(4, course.getDuration());
         st.setBoolean(5, course.isActive());
         st.setDate(6, Date.valueOf(course.getCreatedAt()));
         st.setLong(7, course.getId());
         int numLines = st.executeUpdate();
         if (numLines != 1){
            throw new EntityNotFoundException("Non e' stato trovato il corso con quell'id");
         }
      } catch (SQLException e) {
         e.printStackTrace();
         throw new DataException("errore nella lettura dei corsi da database", e);
      }

   }

   @Override
   public void deleteById(long id) throws EntityNotFoundException, DataException {
      try (
            PreparedStatement st = con.prepareStatement(DELETE_COURSE_BY_ID);//factory method pattern
      ) {
         st.setLong(1, id);
         int numLines = st.executeUpdate();
         if (numLines != 1) {
            throw new EntityNotFoundException("Non e' stato trovato il corso con quell'id");
         }
      } catch (SQLException e) {
         e.printStackTrace();
         throw new DataException("Errore nella lettura dei corsi da database", e);
      }
   }

   @Override
   public int countActiveCourses() throws DataException{
      try (
            Statement st = con.createStatement();//factory method pattern
            ResultSet rs = st.executeQuery(ACTIVE_COURSES)){
         rs.next();
         return rs.getInt(1);

      }catch (SQLException e) {
         e.printStackTrace();
         throw new DataException("errore nella lettura dei corsi da database", e);
      }

   }

   @Override
   public void deactivateOldest(int n) throws DataException {
      try(
            PreparedStatement st = con.prepareStatement(DEACTIVATE_OLDEST_N_COURSES)) {
         st.setInt(1, n);
         st.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
         throw new DataException("Errore nella lettura dei corsi da database", e);
      }
   }

   @Override
   public boolean adjustActiveCourses(int NumActive) throws DataException {
      return false;
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
}
