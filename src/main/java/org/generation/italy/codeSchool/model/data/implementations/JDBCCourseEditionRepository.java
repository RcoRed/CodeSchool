package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Optional;

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

    public Iterable<CourseEdition> findByTeacherId(long id){
        return null;
    }
}
