package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CourseEditionRepository {

    double getCourseEditionTotalCost(); //un metodo che ritorni la somma di tutti i costi per tutte le courseEdition registrate

    Optional<CourseEdition> getMostExpensiveCourseEdition(); // un metodo che restituisca la courseEdition col costo maggiore

    double getAverageCost(); // un metodo che ritorni il valore medio dei costi delle courseEdition

    List<Double> getCourseEditionsDuration(); // un metodo che ritorni una lista delle durate di tutte le courseEdition

    Optional<CourseEdition> getCourseEditionByCourseId(long id); // un metodo che ritorni tutte le courseEdition per un corso

    // un metodo che ritorni tutte le courseEdtion che sono relative ad un corso con una certa parola nel titolo
    // e che sono partite entro un range di date inserite come input
    List<CourseEdition> getCourseEditionByCourseByTitleAndDateRange(String title, LocalDate fromDate, LocalDate toDate);

     List<CourseEdition> getMedianCourseEdition(); // un metodo che ritorni l'edizione corso che ha il costo mediano. Se sono pari, si farà la media tra i due in mezzo

    Optional<CourseEdition> getModeCourseEdition(); // un metodo che ritorni il corso che ha il valore moda del costo (costo che appare più volte nella distribuzione)
}
