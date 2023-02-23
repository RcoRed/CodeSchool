package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryCourseEditionRepository implements CourseEditionRepository {

    private Map<Long, CourseEdition> editionDataSource = new HashMap<>();
    List<CourseEdition> editionCollection = new ArrayList<>(editionDataSource.values());
    @Override
    public double getTotalCost() {
        return editionCollection.stream().mapToDouble(CourseEdition :: getCost).sum();
    }

    @Override
    public CourseEdition findMostExp() {
        List<CourseEdition> me = editionCollection.stream()
                                                  .sorted((ce1, ce2) ->(int)(ce1.getCost() - ce2.getCost()))
                                                  .toList();
        return me.get(0);

    }

    @Override
    public double getAvgCost() {
        return getTotalCost()/editionCollection.size();
    }

    @Override
    public List<Double> getDurations() {
        return editionCollection.stream().map(e -> e.getCourse().getDuration()).toList();
        //mapToDouble crea un DoubleStream, ovvero uno Stream di primitive Double. Esiste uno Stream speciale per ogni primitiva.

    }

    @Override
    public List<CourseEdition> getEditions(long courseID) {
        return null;
    }

    @Override
    public List<CourseEdition> findByContainsInTime(String part, LocalDate start, LocalDate end) {
        return null;
    }

    @Override
    public CourseEdition findByMedianValue() {
        return null;
    }

    @Override
    public CourseEdition findMostCommonCost() {
        return null;
    }
}
