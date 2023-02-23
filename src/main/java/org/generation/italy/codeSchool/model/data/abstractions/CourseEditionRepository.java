package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.List;

public interface CourseEditionRepository {
    double getTotalCost();
    CourseEdition getMostExpensive();
    double getAverageCost();
    Iterable<Double> getDurationList();
    Iterable<CourseEdition> getCourseEditionsById(long courseId);
    Iterable<CourseEdition> getCourseEditionsInTime(String name, LocalDate from, LocalDate to);
    Iterable<CourseEdition> getMiddleCourseEdition();
    Course getMode();

}
