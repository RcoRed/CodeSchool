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
    static{
        System.out.println("inizializzazione statica");
        Driver d=new Driver();
        try {
            DriverManager.registerDriver(d);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
    @Override
    public List<Course> findAll() throws DataException{
        try(Connection con = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
            Statement st = con.createStatement();//factory method pattern
            ResultSet rs = st.executeQuery(COURSE_QUERY);
        ){
            //Class.forName("org.postgresql.Driver");   //hack
            List<Course> courseList = new ArrayList<>();
            while (rs.next()){
                Course c = new Course(rs.getLong("id"),
                                      rs.getString("title"),
                                      rs.getString("description"),
                                      rs.getString("program"),
                                      rs.getDouble("duration"),
                                      rs.getBoolean("is_active"),
                                      rs.getDate("created_at").toLocalDate());
                courseList.add(c);
            }
            return courseList;
            /*for(Course course: courseList){
                System.out.println(course.getTitle());
            }*/
            //courseList.forEach(c-> System.out.println(c.getTitle()));
            //courseList.forEach(System.out::println);
            /*courseList.stream().map(Course::getTitle)
                               .forEach(System.out::println);*/
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataException("errore nella lettura dei corsi da database",e);
        }

    }

    @Override
    public Optional<Course> findById(long id) throws DataException {
        try(Connection con = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
            PreparedStatement st = con.prepareStatement(FIND_COURSE_BY_ID);//factory method pattern
        ){
            st.setLong(1,id);
            try(ResultSet rs = st.executeQuery()){
                if(rs.next()){
                    Course c = new Course(rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("program"),
                            rs.getDouble("duration"),
                            rs.getBoolean("is_active"),
                            rs.getDate("created_at").toLocalDate());
                    return Optional.of(c);
                }
                return Optional.empty();
            }

        }catch(SQLException e){
            e.printStackTrace();
            throw new DataException("errore nella lettura dei corsi da database",e);
        }
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
        try(Connection con = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
            PreparedStatement st = con.prepareStatement(DELETE_COURSE_BY_ID);//factory method pattern
        ){
            st.setLong(1,id);
            int numLines = st.executeUpdate();
            if(numLines!=1){
                throw new EntityNotFoundException("Non e' stato trovato il corso con quell'id");
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataException("errore nella lettura dei corsi da database",e);
        }
    }

    @Override
    public int getActiveCourses() {
        return 0;
    }

    @Override
    public boolean adjustActiveCourses(int NumActive) throws DataException {
        return false;
    }
}
