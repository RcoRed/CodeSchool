package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.entities.Course;
import org.postgresql.Driver;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.codeSchool.model.data.JDBCConstants.*;

public class JDBCCourseRepository implements CourseRepository {

    /*public static int askToClient;
    static { //blocco statico, viene eseguito come prima cosa, è il costruttore statico della classe,
             // serve a dare un valore iniziali alle variabili statiche della classe
        System.out.println("Inizializzazione statica"); // hack
        Driver d = new Driver();
        try {
            DriverManager.registerDriver(d);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    @Override
    public List<Course> findAll() throws DataException{

        try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            Statement st = con.createStatement(); //factory method pattern
            ResultSet rs = st.executeQuery(COURSE_QUERY)) {
            //Class.forName("org.postgresql.Driver");  // hack
            List<Course> courseList = new ArrayList<>();
            while (rs.next()){
                courseList.add(new Course(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("program"),
                        rs.getDouble("duration"),
                        rs.getBoolean("is_active"),
                        rs.getDate("create_at").toLocalDate())
                );
            }
        return courseList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Errore nella lettura dei corsi da database", e);
        }
    }

    @Override
    public Optional<Course> findById(long id) throws DataException {
        try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            PreparedStatement st = con.prepareStatement(FIND_COURSE_BY_ID)) {
            st.setLong(1, id);
            try(ResultSet rs = st.executeQuery()){
                if(rs.next()) {
                    return Optional.of(databaseToJava(rs));
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
        try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            PreparedStatement st = con.prepareStatement(FIND_COURSES_BY_TITLE_CONTAIN);) {
            st.setString(1, part);
            try(ResultSet rs = st.executeQuery()) {
                List<Course> courseList = new ArrayList<>();
                do {
                    courseList.add(databaseToJava(rs));
                } while (rs.next());

                return courseList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Errore nella lettura dei corsi da database", e);
        }
    }

    @Override
    public Course create(Course course) throws DataException {
        try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            PreparedStatement st = con.prepareStatement(CREATE_COURSE)) {
            modifyDatabase(course, st);
            try(ResultSet rs = st.executeQuery()){
                if(rs.next()) {
                    return databaseToJava(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Errore nella lettura dei corsi da database", e);
        }
    }

    @Override
    public void update(Course course) throws EntityNotFoundException, DataException {
        try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            PreparedStatement st = con.prepareStatement(UPDATE_COURSE)) {
            modifyDatabase(course, st);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Errore nella lettura dei corsi da database", e);
        }
    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException, DataException {
        try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            PreparedStatement st = con.prepareStatement(DELETE_COURSES_BY_ID)) {
            st.setLong(1, id);
            int numLines = st.executeUpdate();
            if (numLines!=1){
                throw new EntityNotFoundException("Non è stato trovato il corso con quel id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Errore nella lettura dei corsi da database", e);
        }
    }

    @Override
    public ArrayList<Course> createListOfActiveCourses() throws DataException {
        try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            PreparedStatement st = con.prepareStatement(FIND_ACTIVE_COURSES)) {
            try(ResultSet rs = st.executeQuery()) {
                ArrayList<Course> courseList = new ArrayList<>();
                do{
                    courseList.add(databaseToJava(rs));
                } while (rs.next());
                return courseList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Errore nella lettura dei corsi da database", e);
        }
    }

    @Override
    public void cancelOldActiveCourses(int difference) throws EntityNotFoundException, DataException {
        try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            PreparedStatement st = con.prepareStatement(DELETE_OLD_ACTIVE_COURSES)) {
            st.setInt(1, difference);
            st.setInt(2, difference);
            int numLines = st.executeUpdate();
            if (numLines!=difference){
                throw new EntityNotFoundException("Non sono stati trovati sufficenti corsi attivi");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Errore nella lettura dei corsi da database", e);
        }
    }

    public Course databaseToJava(ResultSet rs) throws DataException {
        try {
            return new Course(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("program"),
                    rs.getDouble("duration"),
                    rs.getBoolean("is_active"),
                    rs.getDate("create_at").toLocalDate());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Errore nella lettura dei corsi da database", e);
        }
    }

    public void modifyDatabase(Course course, PreparedStatement st){
        try {
            st.setLong(1, course.getId());
            st.setString(2, course.getTitle());
            st.setString(3, course.getDescription());
            st.setString(4, course.getProgram());
            st.setDouble(5, course.getDuration());
            st.setBoolean(6, course.isActive());
            st.setDate(7, Date.valueOf(course.getCreateAt()));
            st.setLong(8, course.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
/*for (Course course: courseList){
                System.out.println(course.getTitle());
            }*/
//courseList.forEach(c -> System.out.println(c.getTitle()));
//courseList.forEach(System.out::println);

            /*courseList.stream()
                    .map(Course::getTitle)
                    .forEach(System.out::println); PER STAMPARE*/
