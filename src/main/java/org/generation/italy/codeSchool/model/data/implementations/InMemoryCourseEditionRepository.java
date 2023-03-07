package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class InMemoryCourseEditionRepository implements CourseEditionRepository{

    private Map<Long, CourseEdition> data = new HashMap<>();
    private static long nextId;

    @Override
    public double getCourseEditionTotalCost() {
        Stream<CourseEdition> stream = data.values().stream();
        return stream.mapToDouble(CourseEdition::getCost).sum();
    }

    @Override
    public Optional<CourseEdition> getMostExpensiveCourseEdition() {
        Stream<CourseEdition> stream = data.values().stream();
        //Optional<CourseEdition> optional = stream.max((o1, o2) -> (int)Math.signum(o1.getCost() - o2.getCost()));
        Optional<CourseEdition> optional = stream.max(Comparator.comparingDouble(CourseEdition::getCost));
        return optional;
    }

    @Override
    public double getAverageCost() {
        Stream<CourseEdition> stream = data.values().stream();
        double sum = stream.mapToDouble(CourseEdition::getCost).sum();
        double avg = sum / data.size();
        return avg;
    }

    @Override
    public List<Double> getCourseEditionsDuration() {
        Stream<CourseEdition> stream = data.values().stream();
        var os = stream.map(e -> e.getCourse().getDuration());
        var ps = stream.mapToDouble(e -> e.getCourse().getDuration());
        var totalDuration = stream.map(e -> e.getCourse().getDuration()).toList();
        return totalDuration;
    }

    @Override
    public List<CourseEdition> getCourseEditionsByCourseId(long id) {
        Stream<CourseEdition> stream = data.values().stream();
        List<CourseEdition> idList = stream.filter(e -> e.getCourse().getId() == id).toList();
        return idList;
    }

    @Override
    public List<CourseEdition> getCourseEditionByCourseByTitleAndDateRange(String title, LocalDate fromDate, LocalDate toDate) {
        Stream<CourseEdition> stream = data.values().stream();
        List<CourseEdition> courses = stream.filter(e -> e.getCourse().getTitle().contains(title) && 
                e.startedInRange(fromDate, toDate)).toList();
        return courses;
    }

    @Override
    public List<CourseEdition> getMedianCourseEdition() {
        Stream<CourseEdition> stream = data.values().stream().sorted(Comparator.comparingDouble(CourseEdition::getCost));
        List<CourseEdition> sorted = stream.toList();
        int pos = 0;
        List<CourseEdition> list = new ArrayList<>();
        if(sorted.size() % 2 != 0){ // dispari
             pos = sorted.size() / 2;
             list.add(sorted.get(pos));
             return list;

        } else {
            pos = sorted.size() / 2;
            list.add(sorted.get(pos - 1));
            list.add(sorted.get(pos));
            return list;
        }        
    }

    @Override
    public Optional<Double> getCourseEditionCostMode() {
        Stream<CourseEdition> stream = data.values().stream();
//        var x = stream.collect(Collectors.groupingBy(CourseEdition::getCost)); // chiave: costo, valore: lista
//        var max = x.entrySet().stream().max((kv1, kv2) -> kv1.getValue().size() - kv2.getValue().size()); // cerchiamo la list più lunga (srà la nostra moda)
        var x = stream.collect(Collectors.groupingBy(CourseEdition::getCost, Collectors.counting())); // chiave: costo, valore: lista
        var max = x.entrySet().stream().max(Comparator.comparingLong(Map.Entry::getValue));

        return max.map(Map.Entry::getKey);
//        return max.isPresent()? Optional.of(max.get().getValue().get(0)) : Optional.empty(); // se è vero (return...) se è falso (return...)

//        if(max.isPresent()){
//            return Optional.of(max.get().getKey());
//        }
//        return Optional.empty();
    }

    public CourseEdition createEdition(CourseEdition course){
        nextId++;
        data.put(nextId, course);
        course.setId(nextId);
        return course;
    }
}
