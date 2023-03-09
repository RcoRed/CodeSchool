package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.codeSchool.model.entities.CourseEdition;
import org.generation.italy.codeSchool.model.entities.Teacher;

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
    public Optional<CourseEdition> findMostExpensive() throws SQLException {
        try(Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(FIND_MOST_EXPENSIVE_COURSE);
        ){
            if(rs.next()){
                return Optional.of(databaseToCourseEdition(rs));
            }
            return Optional.empty();
        } catch (SQLException e){
            throw new SQLException("Errore nella lettura dei corsi da database", e);
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
    public List<CourseEdition> findByCourse(long courseId) throws SQLException {
        try(PreparedStatement st = con.prepareStatement(FIND_BY_COURSE)){
            st.setLong(1, courseId);
            List<CourseEdition> list = new ArrayList<>();

            try (ResultSet rs = st.executeQuery()){
                while (rs.next()){
                    list.add(databaseToCourseEdition(rs));
                }
            }
            return list;
        } catch (SQLException e){
            throw new SQLException("Errore nella lettura dei corsi da database", e);
        }
    }

    @Override
    public List<CourseEdition> findByCourseTitleAndPeriod(long courseId, String titlePart, LocalDate startAt, LocalDate endAt) throws SQLException {
        try (PreparedStatement st = con.prepareStatement(FIND_BY_COURSE_AND_TITLE);) {
            st.setLong(1, courseId);
            st.setString(2, titlePart);
            st.setDate(3, Date.valueOf(startAt));
            st.setDate(4, Date.valueOf(endAt));
            List<CourseEdition> list = new ArrayList<>();

            try(ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    list.add(databaseToCourseEdition(rs));
                }
            }
            return list;

        } catch (SQLException e) {
            throw new SQLException("Errore nella lettura dei corsi da database", e);
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
    public List<CourseEdition> findByTeacherId(long teacherId) { // TODO
        return null;
    }

    public CourseEdition databaseToCourseEdition(ResultSet rs) throws SQLException {
        try{
            return new CourseEdition(
                    rs.getLong("id_course_edition"),
                    rs.getString("title"),
                    rs.getDate("started_at").toLocalDate(),
                    rs.getDouble("price"),
                    rs.getLong("id_classroom")
            );
        } catch (SQLException e) {
            throw new SQLException("errore nella lettura dei corsi da database", e);
        }
    }
}

/*
Esercizio:
    * Implementare una serie di metodi nella classe JDBCCourseEditionRepository
    * Tutti i metodi dovranno riportare, oltre alla CourseEdition, anche il relativo Course e la Classroom
    * 1. findMostExpensive()
    * 2. findByCourse()
    * 3. findByCourseTitleAndPeriod()
    * 4. findByTeacherId() -> dammi tutte le CourseEdition tenute dal Teacher con id = x
*/
