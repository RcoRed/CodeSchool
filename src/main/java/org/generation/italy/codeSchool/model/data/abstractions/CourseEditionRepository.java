package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.List;

public interface CourseEditionRepository {

    double getTotalCost();
    CourseEdition findMostExp();
    double getAvgCost();
    List<Double> getDurations();
    List<CourseEdition> getEditions(long courseID);
    List<CourseEdition> findByContainsInTime(String part, LocalDate start, LocalDate end);
    CourseEdition findByMedianValue();
    CourseEdition findMostCommonCost();


}
