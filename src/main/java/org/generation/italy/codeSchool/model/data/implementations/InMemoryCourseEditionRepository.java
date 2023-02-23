package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryCourseEditionRepository implements CourseEditionRepository {

    private Map<Long, CourseEdition> editionDataSource = new HashMap<>();
    private List<CourseEdition> editionCollection = (ArrayList<CourseEdition>) editionDataSource.values();
    @Override
    public double getTotalCost() {
        return editionCollection.stream().mapToDouble(CourseEdition::getCost).sum();
    }

    @Override
    public CourseEdition getMostExpensive() {
        List<CourseEdition> me = editionCollection.stream().
                                                   sorted((ce1, ce2) -> (int) (ce1.getCost() - ce2.getCost())).
                                                   toList();
        return me.get(0);
    }

    @Override
    public double getAverageCost() {
        return getTotalCost() / editionCollection.size();
    }

    @Override
    public List<Double> getDurationList() {
        return editionCollection.stream().map(e -> e.getCourse().getDuration()).toList();
    }

    @Override
    public List<CourseEdition> getCourseEditionsById(long courseId) {
        return null;
    }

    @Override
    public List<CourseEdition> getCourseEditionsInTime(String name, LocalDate from, LocalDate to) {
        return null;
    }

    @Override
    public Course getModeCosts() {
        return null;
    }
}
