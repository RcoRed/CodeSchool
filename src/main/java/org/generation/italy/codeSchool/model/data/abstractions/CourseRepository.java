package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CourseRepository {

    Optional<Course> findById(long id) throws DataException;

    List<Course> findByTitleContains(String part) throws DataException;

    Course create(Course course) throws DataException;

    void update(Course course) throws EntityNotFoundException,DataException;

    void deleteById(long id) throws EntityNotFoundException,DataException;
    ArrayList<Course> createListOfActiveCourses();
    void cancelOldActiveCourses(int difference) ;
}

/*
    utitlizziamo le exceptions che abbimo creato noi (nel package "exceptions")
    così da riuscire a poter utilizzare questa interfaccia con tutte le classi che vogliamo
    e gestire l'errore in maniera SPECIFICA!
 */

    /*
      Crea un altro repository chiamato CourseEditionRepository con Interfaccia
      con una sola implementazione InMemory, ma senza nessun ciclo for!!

      terrà i dati in una mappa di CourseEdition

      CER dovrà avere i seguenti metodi:
      1. dammi somma di tutti i costi per tutte le CE
      2. restituire CE con costo maggiore
      3. valor medio dei costi delle CE
      4. lista delle durate di tutte le CE
      5. tutte le CE per un certo corso (usando id)
      6. tutte le CE relative a un corso con una certa parola nel titolo e che sono partite
            tra due date (inizio e fine range)
      7. voglio CE con costo mediano (il corso che se ordino i corsi con il costo, è quello che sta in mezzo),
            se pari facciamo il valore medio dei due in mezzo.
      8. voglio il corso che ha il valore moda del costo (il corso con costi che appare più volte)
            (più corsi se più corsi)

    */
