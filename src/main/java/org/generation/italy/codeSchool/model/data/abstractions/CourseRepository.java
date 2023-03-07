package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    List<Course> findAll() throws DataException;

    Optional<Course> findById(long id) throws DataException;

    List<Course> findByTitleContains(String part) throws DataException;

    Course create(Course course) throws DataException;

    void update(Course course) throws EntityNotFoundException,DataException;

    void deleteById(long id) throws EntityNotFoundException,DataException;
    int countActiveCourses() throws DataException;
    void deactivateOldest(int n) throws DataException, EntityNotFoundException;
    boolean adjustActiveCourses(int NumActive) throws DataException;

}

/*
    Utilizziamo le exceptions che abbiamo creato noi (nel package "exceptions")
    così da riuscire a poter utilizzare questa interfaccia con tutte le classi che vogliamo
    e gestire l'errore in maniera SPECIFICA!

    Creiamo un altro repository chiamato CourseEditionRepository con Interfaccia
    con una sola implementazione InMemory, ma senza usare nemmeno un ciclo for

    Terrà i dati in una mappa di CourseEdition.

    La CourseEditionRepository dovrà avere i seguenti metodi:
    1. Restituisce la somma di tutti i costi per tutte le CourseEdition
    2. restituisce la CourseEdition che ha il costo maggiore
    3. Restituisce il valor medio dei costi delle CourseEdition
    4. Restituisce una lista delle durate di tutte le CourseEdition
    5. Restituisce tutte le CourseEdition per un certo Course (usando l'id)
    6. Restituisce tutte le CourseEdition relative a un Course con una certa parola nel titolo
        e che sono partite entro un range di date che inseriremo come input
        Es.: tutte le CourseEdition relative ai Corsi che contengono la parola "Java" e che sono partiti tra
        il 2007 e il 2012
    7. Restituisce la CourseEdition ha il costo mediano
        Se sono pari, restituiremo la media fra i due che sono nel mezzo
        Es.: [1, 2, 3, 4] -> (2 + 3) / 2 = 2.5
    8. Restituisce il Course che ha come costo la moda della distribuzione dei corsi (quello che appare più votle)
 */
