package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.entities.Course;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.codeSchool.model.data.JDBCConstants.*;

public class JDBCCourseRepository implements CourseRepository {

//    static{
//        System.out.println("inizializzazione statica");
//        Driver d = new Driver();
//        try {
//            DriverManager.registerDriver(d);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public List<Course> findAll() throws DataException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);  //factory method pattern
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(COURSE_QUERY)
        ){
            List<Course> cs = new ArrayList<>();
            while (rs.next()){
                Course c = new Course(rs.getLong("course_id"),
                                      rs.getString("course_title"),
                                      rs.getString("course_description"),
                                      rs.getString("course_program"),
                                      rs.getDouble("course_duration"),
                                      rs.getBoolean("course_is_active"),
                                      rs.getDate("course_created_at").toLocalDate());
                cs.add(c);
            }
            return cs;
//            for (Course c1 : cs){
//                System.out.println(c1.getTitle());
//            }
            //cs.forEach(c -> System.out.println(c.getTitle()));
            //cs.forEach(System.out::println);
//            cs.stream().map(Course::getTitle)
//                       .forEach(System.out::println);
        } catch (SQLException e) {
            throw new DataException("Errore nella lettura dei corsi da database", e);
        }
    }

    @Override
    public Optional<Course> findById(long id) throws DataException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement st = con.prepareStatement(FIND_COURSE_BY_ID)
        ){
            st.setLong(1,id);
            try(ResultSet rs = st.executeQuery()){
                if (rs.next()) {
                    Course c = new Course(rs.getLong("course_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("course_program"),
                            rs.getDouble("duration"),
                            rs.getBoolean("is_active"),
                            rs.getDate("create_date").toLocalDate()
                    );
                    return Optional.of(c);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Errore nella lettura dei corsi da database", e);
        }
    }

    @Override
    public List<Course> findByTitleContains(String part) throws DataException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement st = con.prepareStatement(FIND_COURSE_BY_TITLE)
        ){
            st.setString(1,"%" + part + "%");
            try(ResultSet rs = st.executeQuery()){
                List<Course> cl = new ArrayList<>();
                while(rs.next()) {
                    Course c = new Course(rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("course_program"),
                            rs.getDouble("duration"),
                            rs.getBoolean("is_active"),
                            rs.getDate("create_date").toLocalDate()
                    );
                    cl.add(c);
                }
                return cl;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Errore nella lettura dei corsi da database", e);
        }
    }

    @Override
    public Course create(Course course) throws DataException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement st = con.prepareStatement(CREATE_COURSE_RETURNING_ID, Statement.RETURN_GENERATED_KEYS)
        ){
            st.setString(1,course.getTitle());
            st.setString(2,course.getDescription());
            st.setString(3,course.getProgram());
            st.setDouble(4, course.getDuration());
            st.setBoolean(5, course.isActive());
            st.setDate(6, Date.valueOf(course.getCreatedAt()));
            st.executeUpdate();
            try(ResultSet keys = st.getGeneratedKeys()) {
                keys.next();
                long key = keys.getLong(1);
                course.setId(key);
                return course;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Errore nella lettura dei corsi da database", e);
        }
    }

    @Override
    public void update(Course course) throws EntityNotFoundException, DataException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement st = con.prepareStatement(UPDATE_COURSE)
        ){
            st.setLong(7, course.getId());
            st.setString(1, course.getTitle());
            st.setString(2, course.getDescription());
            st.setString(3, course.getProgram());
            st.setDouble(4, course.getDuration());
            st.setBoolean(5, course.isActive());
            st.setDate(6, Date.valueOf(course.getCreatedAt()));
            int result = st.executeUpdate();
            if(result != 1){
                throw new EntityNotFoundException("Nessun corso con questo id trovato!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Errore nella lettura dei corsi da database", e);
        }
    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException, DataException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement st = con.prepareStatement(DELETE_COURSE_BY_ID)
        ){
            st.setLong(1, id);
            int result = st.executeUpdate();
            if(result != 1){
                throw new EntityNotFoundException("Non è stato trovato il corso con id" + id + "da cancellare");
            }
        } catch (SQLException e) {
            throw new DataException("Errore nella lettura dei corsi da database", e);
        }
    }

    @Override
    public int countActiveCourses() throws DataException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(ACTIVE_COURSES)
        ){
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            throw new DataException("Errore nella lettura dei corsi da database", e);
        }
    }

    @Override
    public void deactivateOldest(int n) {

    }

    @Override
    public boolean adjustActiveCourses(int NumActive) throws DataException {
        return false;
    }
}

