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
        try (
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(FIND_MOST_EXPENSIVE_COURSE_EDITION);
                ) {
                    if (rs.next()) {
                        return Optional.of(databaseToCourseEdition(rs, databaseToCourse(rs), databaseToClassroom(rs)));
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
        try (PreparedStatement ps = con.prepareStatement(FIND_COURSE_EDITION_BY_COURSE)){
            ps.setLong(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                List<CourseEdition> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(databaseToCourseEdition(rs, databaseToCourse(rs), databaseToClassroom(rs)));
                }
                return result;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<CourseEdition> findByCourseTitleAndPeriod(String titlePart, LocalDate startAt, LocalDate endAt) {
        try (PreparedStatement ps = con.prepareStatement(FIND_COURSE_EDITION_BY_COURSE_TILE_AND_PERIOD)){
            ps.setString(1, "%"+titlePart+"%");
            ps.setDate(2, Date.valueOf(startAt));
            ps.setDate(3, Date.valueOf(endAt));
            try (ResultSet rs = ps.executeQuery()) {
                List<CourseEdition> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(databaseToCourseEdition(rs, databaseToCourse(rs), databaseToClassroom(rs)));
                }
                return result;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<CourseEdition> findByTeacherId(long teacherId) {
        try (PreparedStatement ps = con.prepareStatement(FIND_COURSE_EDITION_BY_TEACHER_ID)){
            ps.setLong(1, teacherId);
            try (ResultSet rs = ps.executeQuery()) {
                List<CourseEdition> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(databaseToCourseEdition(rs, databaseToCourse(rs), databaseToClassroom(rs)));
                }
                return result;
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
            throw new SQLException("errore nella lettura delle classi da database", e);
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
            throw new SQLException("errore nella lettura delle edizioni corso da database", e);
        }

    }
}
