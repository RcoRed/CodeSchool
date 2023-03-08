package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.entities.CourseEdition;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
        try (
                PreparedStatement st = con.prepareStatement(FIND_MOST_EXPENSIVE_COURSE_EDITION)
        ) {

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("errore nella lettura del course_editon da database", e);
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
        return null;
    }

    @Override
    public Iterable<CourseEdition> findByCourseTitleAndPeriod(long courseId, String titlePart, LocalDate startAt, LocalDate endAt) {
        return null;
    }

    @Override
    public Iterable<CourseEdition> findMedian() {
        return null;
    }

    @Override
    public Optional<Double> getCourseEditionCostMode() {
        return Optional.empty();
    }
}
