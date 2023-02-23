package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.*;
import java.util.stream.Collectors;
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
        Optional<CourseEdition> optional = stream.max((o1, o2) -> (int)Math.signum(o1.getCost() - o2.getCost()));
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
//        var os = stream.map(e -> e.getCourse().getDuration());
//        var ps = stream.mapToDouble(e -> e.getCourse().getDuration());
        var totalDuration = stream.map(e -> e.getCourse().getDuration()).toList();
        return totalDuration;
    }

    @Override
    public Optional<CourseEdition> getCourseEditionByCourseId(long id) {
        Stream<CourseEdition> stream = data.values().stream();
        Optional<CourseEdition> idList = stream.filter(e -> e.getCourse().getId() == id).findAny();
        return idList;
    }

    @Override
    public List<CourseEdition> getCourseEditionByCourseByTitleAndDateRange(String title, LocalDate fromDate, LocalDate toDate) {
        Stream<CourseEdition> stream = data.values().stream();
        List<CourseEdition> courses = stream.filter(e -> e.getCourse().getTitle().contains(title) &&
                e.getCourse().getCreatedAt().isAfter(fromDate) && e.getCourse().getCreatedAt().isBefore(toDate)).toList();
        return courses;
    }
    @Override
    public List<CourseEdition> getMedianCourseEdition() {
        Stream<CourseEdition> stream = data.values().stream();
        long key = 0;
        List<CourseEdition> list = new ArrayList<>();
        if(data.size() % 2 != 0){ // dispari
            key =(long) data.size() / 2;
             list.add(data.get(key + 1)) ;
            return list;

        } else if (data.size() != 0){
            key = (long) data.size() / 2;
            list.add(data.get(key));
            list.add(data.get(key + 1));
            return list;
        }

        return list;
    }

    @Override
    public Optional<CourseEdition> getModeCourseEdition() {
        Stream<CourseEdition> stream = data.values().stream();
        var x = stream.collect(Collectors.groupingBy(CourseEdition::getCost, Collectors.toList())); // chiave: costo, valore: lista
        var max = x.entrySet().stream().max((kv1, kv2) -> kv1.getValue().size() - kv2.getValue().size()); // cerchiamo la list più lunga (srà la nostra moda)

//        return max.map(kv -> kv.getValue().get(0)); // se kv è vuoto, map ritorna un optional empty
//        return max.isPresent()? Optional.of(max.get().getValue().get(0)) : Optional.empty(); // se è vero (return...) se è falso (return...)

        if(max.isPresent()){
            return Optional.of(max.get().getValue().get(0));
        }
        return Optional.empty();
//        var x = stream.collect(Collectors.groupingBy(CourseEdition::getCost, Collectors.counting())); // chiave: costo, valore: lista
//        var max = x.entrySet().stream().max((kv1, kv2) -> (int)(kv1.getValue() - kv2.getValue()));

//        for(var kv : x.entrySet()){
//            System.out.println(kv.getKey() + " " + kv.getValue());
//        }
    }

    public CourseEdition createEdition(CourseEdition course){
        nextId++;
        data.put(nextId, course);
        course.setId(nextId);
        return course;
    }
}
