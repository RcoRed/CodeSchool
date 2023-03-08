package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.Optional;

public interface CourseEditionRepository {
    double getTotalCost();
    Optional<CourseEdition> findMostExpensive() throws DataException;
    double findAverageCost();
    Iterable<Double> findAllDuration();
    Iterable<CourseEdition> findByCourse(long courseId);
    Iterable<CourseEdition> findByCourseTitleAndPeriod(long courseId, String titlePart,
                                                       LocalDate startAt, LocalDate endAt);
    Iterable<CourseEdition> findMedian();
    Optional<Double> getCourseEditionCostMode();

}


/*
Implementare metodi in classe JDBCCourseEditionRepository

1. findMostExpensive()
2. findByCourse()
3. findByCourseAndTitleAndPeriod()
4. findByTeacherID() -> tutte le course edition tenute dal teacher con id ?
*/