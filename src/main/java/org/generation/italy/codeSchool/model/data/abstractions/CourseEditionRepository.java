package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.List;

public interface CourseEditionRepository {
    double getTotalCost();
    CourseEdition getMostExpensive();
    double getAverageCost();
    List<Double> getDurationList();
    List<CourseEdition> getCourseEditionsById(long courseId);
    List<CourseEdition> getCourseEditionsInTime(String name, LocalDate from, LocalDate to);

    //7. Restituisce la CourseEdition ha il costo mediano
    //   Se sono pari, restituiremo la media fra i due che sono nel mezzo

    Course getModeCosts();

}
