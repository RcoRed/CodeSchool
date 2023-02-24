package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryCourseEditionRepository implements CourseEditionRepository {
   private Map<Long, CourseEdition> editionDataSource = new HashMap<>();
   List<CourseEdition> editionCollection = new ArrayList<> (editionDataSource.values());
   @Override
   public double getTotalCost() {
      double totalCost = editionCollection.stream()
                                          .mapToDouble(CourseEdition::getCost)
                                          .sum();
      return totalCost;
   }

   @Override
   public Optional<CourseEdition> findMostExpensive() {
      return editionCollection.stream()
                              .sorted((ce1,ce2) -> (int)(ce1.getCost() - ce2.getCost()))
                              .toList()
                              .get(0);
   }

   @Override
   public double getAvgCost() {
      return getTotalCost()/editionCollection.size();
   }

   @Override
   public List<Double> getDurations() {
      List<Double> durationList = editionCollection.stream()
                                                   .map(e -> e.getCourse().getDuration())
                                                   .toList();
      return durationList;
   }

   @Override
   public List<CourseEdition> getEditions(long courseId) {
      List<CourseEdition> editionsForIdCourse=
            editionCollection.stream()
                             .filter(courseEdition -> courseEdition.getCourse().getId() == courseId)
                             .collect(Collectors.toList());
      return editionsForIdCourse;
   }

   @Override
   public List<CourseEdition> findContainsInTime(String part, LocalDate start, LocalDate end) {
      List<CourseEdition> titlePartInPeriod =
            editionCollection.stream()
                             .filter(courseEdition -> courseEdition.getCourse().getTitle().contains(part))
                  .filter(courseEdition -> courseEdition.getStartedAt().isAfter(start.minusDays(1)))
                  .filter(courseEdition -> courseEdition.getStartedAt()
                  .plusMonths((long)(double)(courseEdition.getCourse().getDuration())).isBefore(end.plusDays(1)))
                  .collect(Collectors.toList());
      return titlePartInPeriod;
   }

   @Override
   public List<CourseEdition> findByMediaValue() {
     return null;
   }
   //ritornerà una lista con una sola course edition
   //se c'è una sola mediana, due se sono due i course della mediana

   @Override
   public CourseEdition findMostCommon() {
      Stream<Course> cs = editionDataSource.values().stream().filter(e->e.getCost()>1000).map(e->e.getCourse()).distinct();
      List<Course> ls = cs.toList();
      Optional<Course> os =ls.stream().findFirst();
      Optional<Course> mc = ls.stream().max((c1,c2)->(int) (int) (c1.getDuration()-c2.getDuration()));
      return null;
   }
   public static
}
/*


     7. ||  la CourseEdition che ha il costo mediano (non la media, ma quella che
     sta in mezzo nei prezzi 1,3,5 -> il 3... se sono 1,2,3,4 -> 2 e 3 sono in mezzo
     a quel punto faremo la media di quei duo in mezzo (la mediana fra 2 e 3 è 2.5)
     8. Voglio il corso che ha il valore Moda della distribuzione dei costi,
      il costo che appare più volte

 */