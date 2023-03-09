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
    Iterable<CourseEdition> findByCourseTitleAndPeriod(long courseId, String titlePart,
                                                       LocalDate startAt, LocalDate endAt);
    Iterable<CourseEdition> findMedian();
    Optional<Double> getCourseEditionCostMode();

}

/*
Creare classe JDBCCourseEditionRepository e implementare:
 Tutti i metodi dovranno ritornare anche il relativo Course e la Classroom
 1. findMostExpensive()
 2. findByCourse
 3. findByCourseTitleAndPeriod
 4. findByTeacherId -> tutte le courseEdition tenute dal teacher con id in input
 */