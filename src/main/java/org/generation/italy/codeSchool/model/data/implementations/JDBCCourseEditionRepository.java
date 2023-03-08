package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.entities.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.codeSchool.model.data.JDBCConstants.*;
import static org.generation.italy.codeSchool.model.data.JDBCConstantsEdition.*;
@Repository
@Profile("jdbc_edition")
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
    public Optional<CourseEdition> findMostExpensive() throws DataException {
        try (PreparedStatement st = con.prepareStatement(FIND_MOST_EXPENSIVE_COURSE_EDITION)) {
            try (ResultSet rs = st.executeQuery()) {
                return Optional.of(databaseToCourseEdition(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("errore nella lettura del course_editon da database", e);
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
    public Iterable<CourseEdition> findByCourse(long courseId) throws DataException {
        try(PreparedStatement st = con.prepareStatement(FIND_COURSE_EDITION_BY_COURSE_ID)){
            try(ResultSet rs = st.executeQuery()){
                List<CourseEdition> courseEditions = new ArrayList<>();
                do{
                   courseEditions.add(databaseToCourseEdition(rs));
                }while(rs.next());
                return courseEditions;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("errore nella lettura del course_editon da database", e);
        }
    }

    @Override
    public Iterable<CourseEdition> findByCourseTitleAndPeriod(long courseId, String titlePart, LocalDate startAt, LocalDate endAt) throws DataException {
        try(PreparedStatement st = con.prepareStatement(FIND_COURSE_EDITION_BY_COURSE_TITLE_AND_PERIOD)){
            st.setString(1, "%"+titlePart+"%");
            st.setDate(2, Date.valueOf(startAt));
            st.setDate(3, Date.valueOf(endAt));
            try(ResultSet rs = st.executeQuery()){
                List<CourseEdition> courseEditions = new ArrayList<>();
                do{
                    courseEditions.add(databaseToCourseEdition(rs));
                }while(rs.next());
                return courseEditions;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("errore nella lettura del course_editon da database", e);
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

    public Optional<CourseEdition> findByTeacherById(long teacherId) throws DataException {
        try (PreparedStatement st = con.prepareStatement(FIND_COURSE_EDITION_BY_TEACHER_ID)) {
            st.setLong(1, teacherId);
            try (ResultSet rs = st.executeQuery()) {
                return Optional.of(databaseToCourseEdition(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("errore nella lettura del course_editon da database", e);
        }
    }
    private CourseEdition databaseToCourseEdition(ResultSet rs) throws SQLException {
        try {
            return new CourseEdition(rs.getLong("id_course_edition"),
                    databaseToCourse(rs),
                    rs.getDate("created_at").toLocalDate(),
                    rs.getDouble("price"),
                    databaseToClassroom(rs));

        } catch (SQLException e) {
            throw new SQLException("errore nella lettura dei corsi da database", e);
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
                    databaseToRemotePlatform(rs));
        } catch (SQLException e) {
            throw new SQLException("errore nella lettura dei corsi da database", e);
        }
    }
    private RemotePlatform databaseToRemotePlatform(ResultSet rs) throws SQLException {
        try {
            return new RemotePlatform(rs.getLong("id_remote_platform"),
                    rs.getString("name"),
                    rs.getDouble("annual_cost"),
                    rs.getDate("adoptiondate").toLocalDate());
        } catch (SQLException e) {
            throw new SQLException("errore nella lettura dei corsi da database", e);
        }
    }
    /*
    private EditionModule databaseToEditionModule(ResultSet rs) throws SQLException {
        try {
            return new EditionModule(rs.getLong("id_edition_module"),
                    databaseToCourseModule(rs),
                    (Teacher) rs.getObject("id_teacher"),
                    rs.getDate("star_date").toLocalDate(),
                    rs.getDate("end_date").toLocalDate());

        } catch (SQLException e) {
            throw new SQLException("errore nella lettura dei corsi da database", e);
        }
    }
    private CourseModule databaseToCourseModule(ResultSet rs) throws SQLException {
        try {
            return new CourseModule(rs.getLong("id_course_module"),
                    rs.getString("title"),
                    rs.getString("cm_contents"),
                    databaseToCourse(rs),
                    rs.getDouble("duration"),
                    (Level) rs.getObject("level"));
        } catch (SQLException e) {
            throw new SQLException("errore nella lettura dei corsi da database", e);
        }
    }
    */

}
