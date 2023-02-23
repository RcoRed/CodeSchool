package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {

    Optional<Course> findById(long id) throws DataException;

    List<Course> findByTitleContains(String part) throws DataException;

    Course create(Course course) throws DataException;

    void update(Course course) throws EntityNotFoundException,DataException;

    void deleteById(long id) throws EntityNotFoundException,DataException;

    List<Course> getActiveCourses();

    void deleteOldestActiveCourses(int num);
}

/*
    utitlizziamo le exceptions che abbimo creato noi (nel package "exceptions")
    così da riuscire a poter utilizzare questa interfaccia con tutte le classi che vogliamo
    e gestire l'errore in maniera SPECIFICA!

    Creare un altro repository con interfaccia CourseEditionRepository con implementazione in memory.
    L'implementazione in memory terrà i dati in una mappa di CourseEdition

    Il CourseEditionRepository avrà i
    -un metodo che ritorni la somma di tutti i costi per tutte le courseEdition registrate
    -un metodo che restituisca la courseEdition col costo maggiore
    -un metodo che ritorni il valore medio dei costi delle courseEdition
    -un metodo che ritorni una lista delle durate di tutte le courseEdition
    -un metodo che ritorni tutte le courseEdition per un corso
    -un metodo che ritorni tutte le courseEdtion che sono relative ad un corso con una certa parola nel titolo
     e che sono partite entro un range di date inserite come input
    -un metodo che ritorni l'edizione corso che ha il costo mediano. Se sono pari, si farà la media tra i due in mezzo
    -un metodo che ritorni il corso che ha il valore moda del costo (costo che appare più volte nella distribuzione)
 */