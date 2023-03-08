package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryCourseEditionRepository implements CourseEditionRepository {

    private static Map<Long, CourseEdition> data = new HashMap<>();

    @Override
    public double getTotalCost() {
        return data.values().stream().mapToDouble(CourseEdition :: getCost).sum();
    }

    @Override
    public Optional<CourseEdition> findMostExpensive() {
        return data.values().stream().max(Comparator.comparingDouble(CourseEdition :: getCost));
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
    public Iterable<CourseEdition> findByCourseAndTitleAndPeriod(long courseId, String titlePart,
                                                                 LocalDate startAt, LocalDate endAt){
        return data.values().stream().filter(e -> e.getCourse().getTitle().contains(titlePart)
                &&e.isStartedInRange(startAt, endAt)).toList();
                                             /*&& e.getStartedAt().isAfter(startAt)
                                             && e.getStartedAt().isBefore(endAt)).toList();*/
    }

    @Override
    public Iterable<CourseEdition> findMedian() {
        List <CourseEdition> medianPrice = new ArrayList<>();
        var result = data.values().stream().sorted(Comparator.comparingDouble(CourseEdition :: getCost)).toList();
        if(data.size()%2==1) {
            medianPrice.add(result.get((data.size()-1)/2));
            return medianPrice;
        }else {
            medianPrice.add(result.get((data.size()/2)));
            medianPrice.add(result.get((data.size()/2-1)));
            return medianPrice;
        }
    }

    @Override
    public Optional<Double> getCourseEditionCostMode() {
        /*Stream<Course> cs = data.values().stream().filter(e->e.getCost()>1000).map(e->e.getCourse()).distinct();
        List<Course> ls =cs.toList();
        Optional<Course> os=ls.stream().findFirst();
        Optional<Course> mc=ls.stream().max((c1, c2)->(int) (c1.getDuration()-c2.getDuration()));*/

        Stream<CourseEdition> stream =data.values().stream();
        var x = stream.collect(Collectors.groupingBy(CourseEdition :: getCost, Collectors.counting())); //è come se avesse Collectors.toList()
        var max =x.entrySet().stream().max(Comparator.comparingLong(Map.Entry::getValue)); //ritorna optional di Map.Entry
        /*if (max.isPresent()){
            return Optional.of((max.get().getKey()));
        }
        return Optional.empty();*/
        return max.map(Map.Entry::getKey);  //sta traformando un optional di chiave e valore in un optional di chiave(double)
    }
}
