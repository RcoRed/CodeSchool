package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CourseEditionRepository {
    double getTotalCost();
    Optional<CourseEdition> findMostExpensive();
    double findAverageCost();
    Iterable<Double> findAllDuration();
    Iterable<CourseEdition> findByCourse(long courseId);
    Iterable<CourseEdition> findByCourseAndTitleAndPeriod(long courseId, String titlePart, LocalDate startAt, LocalDate endAt);
    Iterable<CourseEdition> findMedian();
    Optional<Double> getCourseEditionCostMode();
}

/**
 * Implementare una classe JDBCCourseEditionRepoistory
 * implementando:
 *  1. findMostExpensive
 *  2. findByCourse
 *  3. findByCourseTitelAndPeriod
 *  4. (OPTIONAL) findByTeacherId() dammi tutte le coursEdition tenute dal Teacher con id = x
 *  quando mi ritorna indietro la courseEdition, voglio anche
 *  che dietro abbia il corso associato
 */