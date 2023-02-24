package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.entities.CourseEdition;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CourseEditionRepository {
   double getTotalCost();
   Optional<CourseEdition> findMostExpensive();
   double getAvgCost();
   List<Double> getDurations();
   List<CourseEdition> getEditions(long courseId);
   List<CourseEdition> findContainsInTime(String part, LocalDate start, LocalDate end);
   List<CourseEdition> findByMediaValue();
   CourseEdition findMostCommon();
}

/*
    creaiamo un altro repository chiamato CourseEditionRpository con interfaccia
    con un solo implementazione di INMemoriCourse... ma senza usare nemmenoun ciclo for

    terrà i dati in una mappa di COurseEdition

    la CER dovrà avere i seguenti metodi:
    1. restituirà la somma di tutti i corsi per tutte le CourseEdition
    2. Restituisce la CE che ha il costo maggiore
    3. Restituisce il valor medio dei costi delle CourseEdition
    4    ||         una lista delle durate di tutte  le ||
    5       ||      tutte le CE per uin certo Course (with id)
    6  ||       tutte le CE relative ad un COurse con una certa parola nel titolo
            e che sono partite entro un range di date che inseriremo come input
            Es.: tutte le CourseEdition realtive ai corsi che contengono
     7. ||  la CourseEdition che ha il costo mediano (non la media, ma quella che
     sta in mezzo nei prezzi 1,3,5 -> il 3... se sono 1,2,3,4 -> 2 e 3 sono in mezzo
     a quel punto faremo la media di quei duo in mezzo (la mediana fra 2 e 3 è 2.5)
     8. Voglio il corso che ha il valore Moda della distribuzione dei costi,
      il costo che appare più volte

 */