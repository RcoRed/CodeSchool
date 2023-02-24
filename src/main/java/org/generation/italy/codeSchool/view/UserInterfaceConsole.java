package org.generation.italy.codeSchool.view;


import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.services.abstractions.AbstractDidacticService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserInterfaceConsole {
    //parla solo con standardDidacticService
    //deve avere un metodo star
    //iniezione di indipendenza dalle console
    //dovrà contenere un menù: inserisci:
    //s per salvare un nuovo corso
    //r per ricerca corsi per titolo
    //i per ricerca corso per id
    //d per cancellare un corso per id
    //u per eseuire update di un corso per id con un sottomenu per i dati del corso
    //j per limitare il numero di corsi attivi ad un certo numero n

    private AbstractDidacticService service;
    private Scanner console = new Scanner(System.in);
    public UserInterfaceConsole(AbstractDidacticService service){
        this.service = service;
    } //Dependencies Injection
    public void userInteraction(){
        welcome();
        char a = console.nextLine().charAt(0);
        while (a != 'e'){
            try {
                switch (a){
                    case 's':
                        addCourse();
                        break;
                    case 'r':
                        researchCourseByTitle();
                        break;
                    case 'i':
                        findCourseByID();
                        break;
                    case 'd':
                        deleteCourseByID();
                        break;
                    case 'u':
                        updateCourseByID();
                        break;
                    case 'j':
                        deleteOldCourses();
                        break;
                    default:
                        System.out.println("\nNon esiste un'opzione associata a questo tasto\n");
                        break;
                }
            }catch (DataException e){
                System.out.println("Errore nella connessione con la sorgente dati");
                System.out.println(e.getCause().getMessage());
            }
            welcome();
            a = console.next().charAt(0);
        }
    }

    private void deleteOldCourses() throws DataException{
        System.out.println("Quanti corsi vuoi eliminare?\nRicorda: verranno eliminati gli n corsi più vecchi");
        int nCoursesToDelete = console.nextInt();
        boolean results = service.adjustActiveCourses(nCoursesToDelete);
        if(!results){
            System.out.println("\nIl numero di corsi da eliminare è pari o inferiore al numero di corsi presenti");
        }else{
            if(nCoursesToDelete == 1){
                System.out.printf("E' stato eliminato %d corso\n", nCoursesToDelete);
            }else{
                System.out.printf("Sono stati eliminati %d corsi\n", nCoursesToDelete);
            }
        }
    }

    private void updateCourseByID() throws DataException{
        System.out.println("Immetti l'id del corso da aggiornare:");
        long idToUpdate = console.nextLong();
        Optional<Course> toUpdate = service.findCourseById(idToUpdate);
        if(toUpdate.isEmpty()){
            System.out.println("\nNon esiste un corso con id " + idToUpdate + " da aggiornare\n");
        }else{
            System.out.println("\nStai aggiornando il corso:\n" + toUpdate);
            String title1 = readString("Immetti il titolo:");
            String description1 = readString("Immetti la descrizione:");
            String program1 = readString("Immetti il programma:");
            double duration1 = readDouble("Immetti la durata in ore:");
            boolean active = readBoolean("Scrivi s per attivare il corso o n per disattivarlo");
            Course c2 = new Course(idToUpdate, title1, description1, program1, duration1, active, LocalDate.now());
            try {
                service.updateCourse(c2);
                System.out.println("\nIl corso è stato aggiornato! Adesso il corso è composto da:\n" + c2 + "\n");
            } catch (EntityNotFoundException e) {
                System.out.printf("Il corso con id %d non è stato trovato", idToUpdate);
            }
        }
    }

    private void deleteCourseByID() throws DataException{
        System.out.println("Immetti l'id del corso da cancellare:");
        long idToDel = console.nextLong();
        try {
            service.deleteCourseById(idToDel);
            System.out.printf("Il corso con id %d è stato cancellato%n", idToDel);
        } catch (EntityNotFoundException e) {
            System.out.printf("Il corso con id %d non è stato trovato", idToDel);
        }
    }

    private void findCourseByID() throws DataException {
        System.out.println("Inserisci l'id del corso da cercare");
        long idToFind = console.nextLong();
        Optional<Course> optionalCourse = service.findCourseById(idToFind);
        if(optionalCourse.isEmpty()){
            System.out.println("\nNon c'è un corso associato a questo id\n");
        }else {
            System.out.println("Ho trovato questo corso\n" + optionalCourse.get());
        }
    }

    private void researchCourseByTitle() throws DataException {
        System.out.println("Inserisci il titolo del corso da cercare:");
        String part = console.next();
        List<Course> result = service.findCoursesByTitleContains(part);
        if(result.size() == 0){
            System.out.println("Non ci sono ancora corsi\n");
        }else if(result.size() == 1){
            System.out.println("Ho trovato questo corso:\n" + result);
        }else{
            System.out.println("Ho trovato questi corsi:\n" + result);
        }
    }

    private void addCourse() throws DataException {
        long id = 0;
        String title = readString("Immetti il titolo:");
        String description = readString("Immetti la descrizione:");
        String program = readString("Immetti il programma:");
        double duration = readDouble("Immetti la durata in ore:");
        boolean active = readBoolean("Scrivi s per attivare il corso o n per disattivarlo");
        Course c = new Course(id, title, description, program, duration, active, LocalDate.now());
        service.saveCourse(c);
        System.out.println("\nCorso salvato!\n");
    }

    public void welcome(){
        System.out.println("Benvenuto all'interfaccia, premi:\ns per salvare un nuovo corso \nr per ricerca corsi" +
                " per titolo \ni per ricerca corso per id\nd per cancellare un corso per id\nu per eseuire update " +
                "di un corso per id\nj per limitare il numero di corsi attivi ad un certo numero n\ne per uscire dal programma");
    }

    public double readDouble(String s){
        do {
            System.out.print(s + " ");
            String s1 = console.nextLine();
            try {
                return Double.parseDouble(s1);
            }catch (NumberFormatException e){
                System.out.println("Formato inserito non valido");
            }
        }while (true);
    }

    public boolean readBoolean(String s){
        do {
            System.out.print(s + " ");
            String s1 = console.nextLine();
            if(s1.equalsIgnoreCase("s")){
                return true;
            } else if(s1.equalsIgnoreCase("n")){
                return false;
            }else {
                System.out.println("Devi inserire s o n");
            }
        }while (true);
    }

    public long readLong(String s){
        do {
            System.out.print(s + " ");
            String s1 = console.nextLine();
            try {
                return Long.parseLong(s1);
            }catch (NumberFormatException e){
                System.out.println("Formato inserito non valido");
            }
        }while (true);
    }

    public String readString(String s){
        System.out.println(s + " ");
        return console.nextLine();
    }
}
