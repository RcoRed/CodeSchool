package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.*;

public class InMemoryCourseEditionRepository implements CourseEditionRepository {

    private static Map<Long, CourseEdition> data = new HashMap<>();

    @Override
    public double getTotalCost() {
        var result = data.values().stream().mapToDouble(CourseEdition :: getCost).sum();
        return result;
    }

    @Override
    public CourseEdition findMostExpensive() {
        var result = data.values().stream().max(Comparator.comparingDouble(CourseEdition :: getCost));
        if(result.isPresent()){
            return result.get();
        }else {
            System.out.println("Non sono presenti CourseEdition");
            return null;
        }
    }

    @Override
    public double findAverageCost() {
        return getTotalCost() / data.size();
    }

    @Override
    public Iterable<Double> findAllDuration() {
        return data.values().stream().map(e -> e.getCourse().getDuration()).toList();
    }

    @Override
    public Iterable<CourseEdition> findByCourse(long courseId) {
        return data.values().stream().filter(e -> e.getId()==courseId).toList();
    }

    @Override
    public Iterable<CourseEdition> findByCourseAndTitleAndPeriod(long courseId, String titlePart, LocalDate startAt, LocalDate endAt) {

        return null;
    }

    @Override
    public Iterable<CourseEdition> findMedian() {
        return null;
    }

    @Override
    public Course findModeByEditionCost() {
        return null;
    }
}
