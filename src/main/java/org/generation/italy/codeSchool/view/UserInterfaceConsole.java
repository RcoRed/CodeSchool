package org.generation.italy.codeSchool.view;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.implementations.CSVFileCourseRepository;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.generation.italy.codeSchool.model.data.implementations.SerializedCourseRepository;
import org.generation.italy.codeSchool.model.services.abstractions.AbstractDidacticService;
import org.generation.italy.codeSchool.model.services.implementations.StandardDidacticService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
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
                       List<Course> coursesList = showCoursesByTitleLike(); //non trova il testo se in maiuscolo
                       printList(coursesList);

                        break;
                    case "i":
                        showCourseByID();
                        break;
                    case "d":
                        deleteCourseById();
                        break;
                    case "u":
                        upDateCourseById();
                        break;
                    case "j":
                        setActiveCourses();
                        break;
                    case "q":
                        break;
                    default:
                        System.out.println("comando non compreso!");
                }

            } catch (DataException e){
                System.out.println("Errore connessione sorgente dati, riprovare piu tardi!");
            }
            
        } while(!answer.equalsIgnoreCase("q"));
    }

    private void setActiveCourses() {
    }

    private void upDateCourseById() {
    }

    private void deleteCourseById() {
    }

    private void showCourseByID() {
    }

    private List<Course> showCoursesByTitleLike() throws DataException {
        String title = getLine("inserisci titolo da cercare");
        List<Course> coursesList = service.findCoursesByTitleContains(title);
        return coursesList;
    }

    private Course saveCourse() throws DataException {
        String title = getLine("inserisci il titolo");
        String desc = getLine("inseerisci descrizione");
        String program = getLine("inserisci il programma");
        double duration = getDouble("inserisci la durata");
        boolean active = getBoolean("il corso è attivo?");
        LocalDate createat = getDate("inserisci data creazione");
        Course c = new Course(0,title,desc,program,duration,active,createat);
        Course result= service.saveCourse(c);
        return result;
    }

    private void showMainMenu() {
        pr("s per salvare un nuovo corso");
        pr("r per cerca corsi per titolo");
        pr("i cerca un corso per id");
        pr("d cancella corso per id");
        pr("u aggiorna corso per id");
        pr("j limita corsi attivi");
        pr("q esci");
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
                pr("formato inserito non corretto");
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
            System.out.print(prompt + "aaaa-mm-gg ");
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
            System.out.print(prompt + " s/n  ");
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

