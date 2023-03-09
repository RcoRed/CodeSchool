package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.entities.CourseEdition;
import org.generation.italy.codeSchool.model.entities.Teacher;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CourseEditionRepository {
    double getTotalCost();
    Optional<CourseEdition> findMostExpensive() throws SQLException;
    double findAverageCost();
    Iterable<Double> findAllDuration();
    List<CourseEdition> findByCourse(long courseId) throws SQLException;
    List<CourseEdition> findByCourseTitleAndPeriod(long courseId, String titlePart,
                                                       LocalDate startAt, LocalDate endAt) throws SQLException;
    Iterable<CourseEdition> findMedian();
    Optional<Double> getCourseEditionCostMode();
    List<CourseEdition> findByTeacherId(long teahcerId);
}
