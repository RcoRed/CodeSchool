package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
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

    public JDBCCourseEditionRepository(Connection con){
        this.con = con;
    }


    @Override
    public double getTotalCost() {
        return 0;
    }

    @Override
    public Optional<CourseEdition> findMostExpensive() {
        try(
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(MOST_EXPENSIVE_COURSE_EDITION)
        ){
            if(rs.next()){
                return Optional.of(databaseToCourseEdition(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        return null;
    }

    @Override
    public Iterable<CourseEdition> findByCourseTitleAndPeriod(String titlePart, LocalDate startAt, LocalDate endAt) throws DataException{
        try (
                PreparedStatement st = con.prepareStatement(FIND_BY_COURSE_TITLE_AND_PERIOD)
        ){
            st.setString(1,"%" + titlePart + "%");
            st.setDate(2, Date.valueOf(startAt));
            st.setDate(3, Date.valueOf(endAt));
            try (ResultSet rs = st.executeQuery()){
                List<CourseEdition> ce = new ArrayList<>();
                while (rs.next()){
                    ce.add(databaseToCourseEdition(rs));
                }
                return ce;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Errore nella ricerca dell'edizione per titolo e periodo", e);
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

    @Override
    public Iterable<CourseEdition> findByTeacherId(long id)throws DataException{
        try (
                PreparedStatement st = con.prepareStatement(FIND_BY_TEACHER_ID)
        ){
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()){
                List<CourseEdition> ce = new ArrayList<>();
                while (rs.next()){
                    ce.add(databaseToCourseEdition(rs));
                }
                return ce;
            }
        } catch (SQLException e) {
            throw new DataException("Errore nella ricerca dell'edizione per insegnante", e);
        }
    }

    private CourseEdition databaseToCourseEdition(ResultSet rs) throws SQLException{
        Classroom cr = new Classroom(rs.getLong(11), rs.getString(12),rs.getInt(13),
                rs.getBoolean(14), rs.getBoolean(15), rs.getBoolean(16),null);
        Course c = new Course(rs.getInt(4), rs.getString(5),rs.getString(6),rs.getString(7),
                rs.getDouble(8), rs.getBoolean(9), rs.getDate(10).toLocalDate());

        return new CourseEdition(rs.getLong(1),c,rs.getDate(2).toLocalDate(),rs.getDouble(3),cr);
    }
}
