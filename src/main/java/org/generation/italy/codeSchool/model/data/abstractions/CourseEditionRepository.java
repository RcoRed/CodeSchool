package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.List;

public interface CourseEditionRepository {
    double getTotalCost();
    CourseEdition findMostExpensive();
    double findAverageCost();
    Iterable<Double> findAllDuration();
    Iterable<CourseEdition> findByCourse(long courseId);
    Iterable<CourseEdition> findByCourseAndTitleAndPeriod(long courseId, String titlePart,
                                                          LocalDate startAt, LocalDate endAt);
    Iterable<CourseEdition> findMedian();
    Iterable<CourseEdition> findModeByEditionCost();

}
