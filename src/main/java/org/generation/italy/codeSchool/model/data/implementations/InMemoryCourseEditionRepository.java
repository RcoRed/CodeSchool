package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.*;

public class InMemoryCourseEditionRepository implements CourseEditionRepository {

    private Map<Long, CourseEdition> editionDataSource = new HashMap<>();
    private List<CourseEdition> editionCollection = (ArrayList<CourseEdition>) editionDataSource.values();
    @Override
    public double getTotalCost() {
        return editionCollection.stream().mapToDouble(CourseEdition::getCost).sum();
    }

    @Override
    public CourseEdition getMostExpensive() {
        return editionCollection.stream().sorted((ce1, ce2) -> (int) (ce1.getCost() - ce2.getCost())).toList().get(0);
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
        return editionCollection.stream().filter(e -> e.getCourse().getId() == courseId).toList();
    }

    @Override
    public List<CourseEdition> getCourseEditionsInTime(String name, LocalDate fromDate, LocalDate toDate) {
        return editionCollection.stream().filter(e -> e.getCourse().getTitle().contains(name)
                    && e.getStartedAt().isAfter(fromDate)
                    && e.getStartedAt().isBefore(toDate)).
                    toList();
    }

    @Override
    public List<CourseEdition> getMiddleCourseEdition() {
        List<CourseEdition> result = new ArrayList<>();
        List<CourseEdition> sorted = editionCollection.stream()
                .sorted(Comparator.comparingDouble(CourseEdition::getCost)).
                toList();
        if (editionCollection.size() % 2 == 0) {
            result.add(sorted.get((sorted.size() / 2)));
            result.add(sorted.get((sorted.size() / 2) + 1));
        } else {
            result.add(sorted.get((sorted.size() + 1) / 2));
        }
        return result;
    }

    @Override
    public Course getMode() {
        return null;

    }
}
