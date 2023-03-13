package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.Optional;

public interface CourseEditionRepository {
    double getTotalCost();
    Optional<CourseEdition> findMostExpensive();
    double findAverageCost();
    Iterable<Double> findAllDuration();
    Iterable<CourseEdition> findByCourse(long courseId);
    Iterable<CourseEdition> findByCourseTitleAndPeriod(String titlePart,
                                                       LocalDate startAt, LocalDate endAt);
    Iterable<CourseEdition> findMedian();
    Optional<Double> getCourseEditionCostMode();

}