package org.generation.italy.codeSchool.view;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.services.abstractions.AbstractDidacticService;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserInterfaceConsole {
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
    private AbstractDidacticService service;
    private Scanner console;

    public UserInterfaceConsole(AbstractDidacticService service){
        this.service = service;
        this.console = new Scanner(System.in);
    }

    public void start(){
        String answer;
        do{
            showMainMenu();
            answer = console.nextLine();
            try {
                switch (answer){
                    case "s":
                        Course c = saveCourse();
                        break;
                    case "r":
                        List<Course> l = showCourseByTitleLike();
                        break;
                    case "i":
                        Optional<Course> o = showCourseById();
                        break;
                    case "d":
                        deleteCourseById();
                        break;
                    case "u":
                        updateCourseById();
                        break;
                    case "j":
                        //setActiveCourses();
                        break;
                    case "q":
                        break;
                    default:
                        System.out.println("Comando non compreso");
                }
            }catch (DataException e){
                System.out.println("Errore connessione sorgente dati");
            } catch (EntityNotFoundException e) {
                pr("Errore nella ricerca");
            }
        }while(!answer.equalsIgnoreCase("q"));

    }

    public void showMainMenu(){
        pr("s -> Salva un nuovo corso;");
        pr("r -> Cerca corsi per titolo");
        pr("i -> Cerca un corso tramite ID");
        pr("d -> Cancella un corso tramite ID");
        pr("u -> Aggiorna un corso tramite ID");
        pr("j -> Limita numero corsi attivi");
        pr("q -> Esci");
    }

    public Course saveCourse() throws DataException {
        String title = getLine("Inserisci titolo corso");
        String desc = getLine("Inserisci la descrizione del corso");
        String program = getLine("Inserisci il programma");
        double duration = getDouble("Durata corso");
        boolean active = getBoolean("Il corso è attivo?");
        LocalDate createdAt = getDate("Inserisci data di creazione");

        Course c = new Course(0, title, desc, program, duration, active, createdAt);
        Course result = service.saveCourse(c);

        return result;
    }

    private List<Course> showCourseByTitleLike() throws DataException {
        String title = getLine("Ricerca corsi");
        List<Course> result = service.findCoursesByTitleContains(title);

        return result;
    }

    public Optional<Course> showCourseById() throws DataException {
        long id = getLong("ID corso");
        Optional<Course> c = service.findCourseById(id);

        return c;
    }

    public void deleteCourseById() throws DataException, EntityNotFoundException {
        long id = getLong("Cancella corso con ID");
        service.deleteCourseById(id);
    }

    public void updateCourseById() throws DataException {
        long id = getLong("Aggiorna corso con ID");
        Optional<Course> c = service.findCourseById(id);
        if(c.isEmpty()){
            System.out.println("Questo corso non esiste");
        } else{
            String title = getLine("Inserisci titolo corso");
            String desc = getLine("Inserisci la descrizione del corso");
            String program = getLine("Inserisci il programma");
            double duration = getDouble("Durata corso");
            boolean active = getBoolean("Il corso è attivo?");
            LocalDate createdAt = getDate("Inserisci data di creazione");

            Course c1 = new Course(0, title, desc, program, duration, active, createdAt);
        }
    }

    private void pr(String s){
        System.out.println(s);
    }

    private void p(String s){
        System.out.print(s + ": ");
    }

    private String getLine(String prompt){
        System.out.print(prompt + ": ");
        return console.nextLine();
    }

    private double getDouble(String prompt){
        do{
            System.out.println(prompt + ": ");
            String answer = console.nextLine();
            try{
                return Double.parseDouble(answer);
            } catch (NumberFormatException e){
                pr("Formato inserito non valido");
            }
        }while(true);
    }

    private long getLong(String prompt){
        do{
            System.out.println(prompt + ": ");
            String answer = console.nextLine();
            try{
                return Long.parseLong(answer);
            } catch (NumberFormatException e){
                pr("Formato inserito non valido");
            }
        }while(true);
    }

    private LocalDate getDate(String prompt){
        do{
            System.out.println(prompt + ": ");
            String answer = console.nextLine();
            try{
                return LocalDate.parse(answer);
            } catch (DateTimeParseException e){
                pr("Formato inserito non valido");
            }
        }while(true);
    }

    private boolean getBoolean(String prompt){
        do{
            System.out.println(prompt + " s/n: ");
            String answer = console.nextLine();
            if(answer.equalsIgnoreCase("s")){
                return true;
            }
            else if(answer.equalsIgnoreCase("n")){
                return false;
            }
            pr("Inserisci s o n");
        }while(true);
    }
}