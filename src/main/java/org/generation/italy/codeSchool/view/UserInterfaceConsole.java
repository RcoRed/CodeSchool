package org.generation.italy.codeSchool.view;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.services.abstractions.AbstractDidacticService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class UserInterfaceConsole {
    private AbstractDidacticService service;
    private Scanner console;

    public UserInterfaceConsole(AbstractDidacticService service) {
        this.service = service;
        this.console = new Scanner(System.in);
    }
    public void start(){
        String answer;
        do{
            showMainMenu();
            answer = console.nextLine();
            try {
                switch (answer) {
                    case "s":
                        Course c = saveCourse();
                        pr("corso salvato con id "+ c.getId());
                        break;
                    case "r":
                        List<Course> coursesList = showCoursesByTitleLike();
                        printList(coursesList);
                        break;
                    case "i":
                        Course course1 = showCourseByID();
                        pr(course1.toString());
                        break;
                    case "d":
                        long deletedId = deleteCourseById();
                        pr("É stato eliminato corretamente il corso con id: " + deletedId);
                        break;
                    case "u":
                        Course course2 = updateCourseById();
                        pr("Il corso è stato aggiornato correttamente con il nuovo/i parametro/i : ");
                        pr(course2.toString());
                        break;
                    case "j":
                        if (setActiveCourses()){
                            pr("Aggiornamento corsi attivi avvenuto con successo");
                        }else {
                            pr("Nessun corso è stato aggiornato");
                        }
                        break;
                    case "q":
                        pr("Termino il programma...");
                        pr("<==== ARRIVEDERCI ====>");
                        break;
                    default:
                        System.out.println("comando non compreso! !Riprova!");
                }

            } catch (EntityNotFoundException e){
                System.out.println("Errore nella ricerca! " + e.getMessage());
            } catch (DataException e){
                System.out.println("Errore connessione sorgente dati, riprovare piu tardi! " + e.getMessage());
            }

        } while(!answer.equalsIgnoreCase("q"));
    }

    private boolean setActiveCourses() throws DataException {
        int id = (int)getLong("Inserisci quanti Corsi (i più recenti) dovranno rimanere attivi");
        return service.adjustActiveCourses(id);
    }

    private Course updateCourseById() throws DataException, EntityNotFoundException {
        long id = getLong("inserisci ID del corso da aggiornare");
        String title = getLine("inserisci il titolo");
        String desc = getLine("inseerisci descrizione");
        String program = getLine("inserisci il programma");
        double duration = getDouble("inserisci la durata");
        boolean active = getBoolean("il corso è attivo?");
        LocalDate createAt = getDate("inserisci data creazione");
        Course c = new Course(id,title,desc,program,duration,active,createAt);
        service.updateCourse(c);
        return c;
    }

    private long deleteCourseById() throws EntityNotFoundException, DataException {
        long id = getLong("inserisci ID da eliminare");
        service.deleteCourseById(id);
        return id;
    }

    private Course showCourseByID() throws EntityNotFoundException, DataException {
        long id = getLong("inserisci ID da cercare");
        Optional<Course> optionalCourse = service.findCourseById(id);
        if (optionalCourse.isPresent()){
            Course course = optionalCourse.get();
            return course;
        }
        throw new EntityNotFoundException("Errore nella ricerca del corso: Corso non trovato");
    }

    private List<Course> showCoursesByTitleLike() throws DataException {
        String title = getLine("inserisci titolo da cercare");
        return service.findCoursesByTitleContains(title);
    }

    private Course saveCourse() throws DataException {
        String title = getLine("inserisci il titolo");
        String desc = getLine("inseerisci descrizione");
        String program = getLine("inserisci il programma");
        double duration = getDouble("inserisci la durata");
        boolean active = getBoolean("il corso è attivo?");
        LocalDate createAt = getDate("inserisci data creazione");
        return service.saveCourse(new Course(0,title,desc,program,duration,active,createAt));
    }

    private void showMainMenu() {
        pr("<==== INTERFACCIA UTENTE ====>");
        pr("|- s -|per salvare un nuovo corso");
        pr("|- r -|cerca corsi per titolo");
        pr("|- i -|cerca un corso per id");
        pr("|- d -|cancella corso per id");
        pr("|- u -|aggiorna corso per id");
        pr("|- j -|limita corsi attivi");
        pr("|- q -|esci");
    }
    private void pr(String s){
        System.out.println(s);

    }
    private void p(String s){
        System.out.print(s+" ");
    }
    private String getLine(String prompt){
        System.out.print(prompt+" ");
        return console.nextLine();
    }
    private double getDouble(String prompt){
        do {
            System.out.print(prompt + " ");
            String answer = console.nextLine();
            try {
                return Double.parseDouble(answer);
            } catch (NumberFormatException e) {
                pr("formato inserito non corretto. !Riprova!");
            }
        }while (true);
    }
    private long getLong(String prompt){
        do {
            System.out.print(prompt + " ");
            String answer = console.nextLine();
            try {
                return Long.parseLong(answer);
            } catch (NumberFormatException e) {
                pr("formato inserito non corretto");
            }
        }while (true);
    }
    private LocalDate getDate(String prompt){
        do {
            System.out.print(prompt + " aaaa-mm-gg ");
            String answer = console.nextLine();
            try {
                return LocalDate.parse(answer);
            } catch (DateTimeParseException e) {
                pr("formato inserito non corretto");
            }
        }while (true);
    }
    private boolean getBoolean(String prompt){
        do {
            System.out.print(prompt + " s/n ");
            String answer = console.nextLine();
            if (answer.equalsIgnoreCase("s")) {
                return true;
            }
            if (answer.equalsIgnoreCase("n")) {
                return false;
            }
            pr("Devi inserire s o n");
        } while (true);

    }
    private void printList(List<Course> list){
        for(Course c : list){
            pr(c.toString());
        }
    }


}
//dovrà parlare solo con StandardDidacticService
//iniezione di indipendeza nella console
//deve avere un metodo start
//dovrà contenere un menù:
// s=salvare un nuovo corso
// r=ricerca per corsi titoloLike
// i=ricerca corso per ID
// d=cancellare un corso per ID
// u=eseguire update di un corso per ID
// j=limitare il numero di corsi attivi ad un certo numero n
