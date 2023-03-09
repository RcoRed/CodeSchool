package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CourseEditionRepository {
    double getTotalCost() throws DataException;
    Optional<CourseEdition> findMostExpensive() throws DataException;
    double findAverageCost() throws DataException;
    Iterable<Double> findAllDuration() throws DataException;
    Iterable<CourseEdition> findByCourse(long courseId) throws DataException;
    Iterable<CourseEdition> findByCourseTitleAndPeriod(String titlePart,
                                                       LocalDate startAt, LocalDate endAt) throws DataException;
    Iterable<CourseEdition> findMedian() throws DataException;
    Optional<Double> getCourseEditionCostMode() throws DataException;

    Iterable<CourseEdition> findByTeacherId(long id) throws DataException;
}

/*
Creare classe JDBCCourseEditionRepository e implementare:
 Tutti i metodi dovranno ritornare anche il relativo Course e la Classroom
 1. findMostExpensive()
 2. findByCourse
 3. findByCourseTitleAndPeriod
 4. findByTeacherId -> tutte le courseEdition tenute dal teacher con id in input
 */