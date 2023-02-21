package org.generation.italy.codeSchool.view;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.generation.italy.codeSchool.model.services.implementations.StandardDidacticService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserInterfaceConsole {
    private StandardDidacticService repo = new StandardDidacticService(new InMemoryCourseRepository());

    public void start() throws DataException, EntityNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Benvenuto! Per cominciare inserisci uno dei seguenti comandi e premi INVIO");
        System.out.println("r ---> Cerca un corso per titolo");
        System.out.println("i ---> Cerca un corso per id");
        System.out.println("d ---> Elimina il corso corrispondente ad un id");
        System.out.println("s ---> Aggiungi un nuovo corso");
        System.out.println("u ---> Aggiorna un corso esistente");
        System.out.println("j ---> Determina il numero di corsi attivi");
        System.out.println("--- Per terminare digita e ---");
        char input = sc.next().charAt(0);
        switch (input) {
            case 'r':
                try {
                    System.out.println("Inserisci il nome del corso che cerchi:");
                    String titlePart = sc.nextLine().toLowerCase();
                    List<Course> coursesContains = repo.findCoursesByTitleContains(titlePart);
                    System.out.println("Ho trovato i seguenti corsi:");
                    for (Course c : coursesContains) System.out.println(c.toString());
                    return;
                } catch (DataException e) {
                    throw new DataException("Error", e);
                }
            case 'i':
                System.out.println("Inserisci l'ID del corso che cerchi:");
                long findId = sc.nextLong();
                Optional<Course> found = repo.findCourseById(findId);
                if (found.isEmpty()) {
                    System.out.println("Corso con id " + findId + " non trovato");
                    return;
                }
                System.out.println("Ho trovato il corso: " + found);
                return;
            case 'd':
                System.out.println("Inserisci l'ID del corso che vuoi eliminare:");
                long deleteId = sc.nextLong();
                if (repo.findCourseById(deleteId).isEmpty()) {
                    System.out.println("Corso non trovato");
                    return;
                }
                repo.deleteCourseById(deleteId);
                return;
            case 's':
                Course added = new Course();
                System.out.println("Pronto per la creazione di un nuovo corso.");
                System.out.println("Inserisci prima il titolo del tuo corso:");
                added.setTitle(sc.nextLine().toLowerCase().replaceAll(" ", ""));
                System.out.println("Inserisci una descrizione per il tuo corso");
                added.setDescription(sc.nextLine());
                System.out.println("Inserisci un programma per il tuo corso");
                added.setProgram(sc.nextLine());
                System.out.println("Inserisci la durata del tuo corso");
                added.setDuration(sc.nextLong());
                System.out.println("Desideri che questo corso sia attivo fin da subito?");
                switch (sc.next().charAt(0)) {
                    case 'y':
                        added.setActive(true);
                        break;
                    case 'n':
                        added.setActive(false);
                        break;
                }
                System.out.println("Questo è il corso che è stato aggiunto:");
                added.setCreatedAt(LocalDate.now());
                repo.saveCourse(added);
                System.out.println(added);
                return;
            case 'u':
                Course updated = new Course();
                System.out.println("Pronto per l'aggiornamento del corso.");
                System.out.println("Inserisci prima l'id del corso da aggiornare:");
                long updatedId = sc.nextLong();
                updated.setId(updatedId);
                System.out.println("Inserisci il nuovo titolo del corso:");
                updated.setTitle(sc.nextLine().toLowerCase().replaceAll(" ", ""));
                System.out.println("Inserisci una descrizione per il corso");
                updated.setDescription(sc.nextLine());
                System.out.println("Inserisci un programma per il corso");
                updated.setProgram(sc.nextLine());
                System.out.println("Inserisci la durata del corso");
                updated.setDuration(sc.nextLong());
                System.out.println("Desideri che il corso sia attivo fin da subito?");
                switch (sc.next().charAt(0)) {
                    case 'y':
                        updated.setActive(true);
                        break;
                    case 'n':
                        updated.setActive(false);
                        break;
                }
                if (repo.findCourseById(updatedId).isEmpty()) {
                    System.out.println("Il corso con id " + updatedId + "non esiste"); // dovrei tirare un'eccezione?
                    return;
                }
                updated.setCreatedAt(repo.findCourseById(updatedId).get().getCreatedAt());
                repo.updateCourse(updated);
                System.out.println("Corso aggiornato correttamente");
                return;
            case 'j':
                System.out.println("Inserisci il numero massimo di corsi attivi che desideri:");
                int maxActives = sc.nextInt();
                if (repo.adjustActiveCourses(maxActives)) {
                    System.out.println("Adesso i corsi attivi sono "+maxActives);
                    break;
                }
                System.out.println("I corsi attivi sono già meno di "+maxActives);
                return;
            case 'e':
                System.out.println("Grazie per aver usato i servizi CodeSchool");
                return;
        }
        sc.close();
    }
}
