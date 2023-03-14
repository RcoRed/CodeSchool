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

/*
* Implementare una serie di metodi nella classe JDBCCourseEditionRepository
* Tutti i metodi dovranno riportare, oltre alla CourseEdition, anche il relativo Course e la Classroom
* 1. findMostExpensive()
* 2. findByCourse()
* 3. findByCourseTitleAndPeriod()
* 4. findByTeacherId() -> dammi tutte le CourseEdition tenute dal Teacher con id = x
*/