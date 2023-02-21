//dovrà parlare solo con StandardDidacticService
//iniezione di dipendenza nella console
//deve avere un metodo start
//dovrà contenere un menù:
// s=salvare un nuovo corso, con sottomenù per mettere i dati del corso
// i=ricerca corso per id
// r=ricerca per corsi titoloLike
// d=cancellare un corso per id
// u=eseguire update di un corso per id, con sottomenù per updatare le varie cose del corso
// j=limitare il numero di corsi attivi ad un certo numero n

package org.generation.italy.codeSchool.view;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.generation.italy.codeSchool.model.data.services.implementations.StandardDidacticService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserInterfaceConsole {
    private Scanner sc = new Scanner(System.in);
    private String askId = "Inserisci id corso: ";
    private String askTitle = "Inserisci il titolo del corso: ";
    private String askDescription = "Inserisci la descrizione del corso: ";
    private String askProgram = "Inserisci il programma del corso: ";
    private String askDuration = "Inserisci la durata del corso: ";
    private String askActive = "Inserisci se il corso è attivo: "; //true or false
    private String askCreateAt = "Inserisci la data di creazione del corso: ";

    //private CourseRepository repo = new InMemoryCourseRepository();
    //private StandardDidacticService service = new StandardDidacticService(repo);
    private StandardDidacticService service;
    public UserInterfaceConsole(StandardDidacticService service) {
        this.service = service;
    }
    public void start() throws DataException, EntityNotFoundException {
        System.out.println("Buongiorno benvenuto nel sistema di gestione corsi.");
        for(;;){
            String s = askCommand("Buongiorno si prega di selezionare l'azione desiderata: ");
            menu(s);
        }
    }
    public String askCommand(String prompt){
        System.out.println(prompt);
        return sc.nextLine();
    }
    public void menu(String s) throws DataException, EntityNotFoundException {

        if(s.equalsIgnoreCase("s")){
            System.out.println("Benvenuto, puoi ora salvare un nuovo corso: ");
            service.saveCourse(subMenu());
        }else if(s.equalsIgnoreCase("i")){
            String ss = "Benvenuto, puoi ora cercare un corso dal suo id: ";
            long idToSearch = Long.parseLong(askCommand(ss));
            System.out.println(service.findCourseById(idToSearch));
        }else if(s.equalsIgnoreCase("r")){
            String ss = "Benvenuto, puoi ora cercare se una stringa di testo è contenuta nel titolo di un corso, inserisci la stringa: ";
            String titleToSearch = askCommand(ss);
            System.out.println(service.findCoursesByTitleContains(titleToSearch).toString());
        }else if(s.equalsIgnoreCase("d")){
            String ss = "Benvenuto, puoi cancellare un corso dal suo id: ";
            long idToDelete = Long.parseLong(askCommand(ss));
            service.deleteCourseById(idToDelete);
            System.out.println("Il corso è stato eliminato");
        }else if(s.equalsIgnoreCase("u")) {
            System.out.println("Benvenuto, puoi ora fare un update ad un corso: ");
            subMenu();
            service.updateCourse(subMenu());
        }else if (s.equalsIgnoreCase("j")) {
            System.out.println("Benvenuto, puoi controllare i corsi attivi.");
            int numMaxActive = Integer.parseInt(askCommand("Inserisci il numero massimo di corsi attivi: "));
            System.out.println(service.adjustActiveCourses(numMaxActive));
        }else if(s.equalsIgnoreCase("stop")){
            System.exit(0);
        }else{
            System.out.println("Il comando inserito non esiste.");
        }
    }
    public Course subMenu(){
        long id = Long.parseLong(askCommand(askId));
        String title = askCommand(askTitle);
        String description = askCommand(askDescription);
        String program = askCommand(askProgram);
        double duration = Double.parseDouble(askCommand(askDuration));
        boolean isActive = Boolean.parseBoolean(askCommand(askActive));
        LocalDate createAt = LocalDate.parse(askCommand(askCreateAt));

        return new Course(id,title,description,program,duration,isActive,createAt);
    }
}
