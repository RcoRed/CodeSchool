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
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.data.services.implementations.StandardDidacticService;

import java.time.LocalDate;
import java.util.*;

public class UserInterfaceConsole {
    private Scanner sc = new Scanner(System.in);
    private String askId = "Inserisci id corso: ";
    private String askTitle = "Inserisci il titolo del corso: ";
    private String askDescription = "Inserisci la descrizione del corso: ";
    private String askProgram = "Inserisci il programma del corso: ";
    private String askDuration = "Inserisci la durata del corso: ";
    private String askActive = "Inserisci se il corso è attivo: "; //true or false
    private String askCreateAt = "Inserisci la data di creazione del corso: "; //yy-mm-dd
    private String password = "java";
    private StandardDidacticService service;
    public UserInterfaceConsole(StandardDidacticService service) {
        this.service = service;
    }
    public void start() throws DataException, EntityNotFoundException {
        System.out.println("Buongiorno benvenuto nel sistema di gestione corsi.");
        putPassword();
    }
    public String askCommand(String prompt){
        System.out.println(prompt);
        return sc.nextLine();
    }
    public void putPassword() throws DataException, EntityNotFoundException {
        for(int i =0; i<2 ;i++) {
            String givePassword = askCommand("Inserire la password per accedere ai comandi premium " +
                    "o 'skip' per accedere ai comandi base: ");
            if (givePassword.equals(password)) {
                System.out.println("Benvenuto nell'area per gli utenti premium. \n"
                        + "Hai a disposizione i seguenti comandi per gli utenti premium: \n"
                        + "s = salvare un nuovo corso.\n"
                        + "d = cancellare un corso per id.\n"
                        + "u = eseguire update di un corso per id.\n"
                        + "j = limitare il numero di corsi attivi ad un certo numero n.\n"
                        + "i = ricerca un corso per id.\n"
                        + "r = ricerca corsi per una stringa.\n"
                        + "stop = termina il progrmamma.\n");
                for (;;) {
                    String premiumCommand = askCommand("Selezionare l'azione premium desiderata: ");
                    menuPremium(premiumCommand);
                }
            } else if (givePassword.equalsIgnoreCase("skip")) {
                System.out.println("Hai a disposizione i seguenti comandi per gli utenti base: \n"
                        + "i = ricerca un corso per id.\n"
                        + "r = ricerca corsi per una stringa.\n"
                        + "stop = termina il progrmamma.\n");
                for (;;) {
                    String command = askCommand("Selezionare l'azione desiderata: ");
                    menu(command);
                }
            } else {
                System.out.println("Password errata, hai " + (2-i) +" tentativi rimasti.");
            }
        }
    }
    public void menu(String command) throws DataException {
        if(command.equalsIgnoreCase("i")){
            String ss = "Puoi ora cercare un corso dal suo id: ";
            long idToSearch = Long.parseLong(askCommand(ss));
            System.out.println(service.findCourseById(idToSearch));
        }else if(command.equalsIgnoreCase("r")){
            String ss = "Puoi ora cercare se una stringa di testo è contenuta nel titolo di un corso, inserisci la stringa: ";
            String titleToSearch = askCommand(ss);
            System.out.println(service.findCoursesByTitleContains(titleToSearch).toString());
        }else if(command.equalsIgnoreCase("stop")){
            System.out.println("Grazie per aver usato il nostro servizio di gestione corsi, arrivederci!");
            System.exit(0);
        }else{
            System.out.println("Il comando inserito non esiste.");
        }
    }

    public void menuPremium(String premiumCommand) throws DataException, EntityNotFoundException {
        if (premiumCommand.equalsIgnoreCase("s")) {
            System.out.println("Puoi ora salvare un nuovo corso.");
            service.saveCourse(subMenu());
        } else if (premiumCommand.equalsIgnoreCase("d")) {
            String ss = "Puoi ora cancellare un corso dal suo id: ";
            long idToDelete = Long.parseLong(askCommand(ss));
            service.deleteCourseById(idToDelete);
            System.out.println("Il corso è stato eliminato");
        } else if (premiumCommand.equalsIgnoreCase("u")) {
            System.out.println("Puoi ora fare un update ad un corso: ");
            subMenu();
            service.updateCourse(subMenu());
        } else if (premiumCommand.equalsIgnoreCase("j")) {
            System.out.println("Puoi ora controllare i corsi attivi.");
            int numMaxActive = Integer.parseInt(askCommand("Inserisci il numero massimo di corsi attivi: "));
            System.out.println(service.adjustActiveCourses(numMaxActive));
        } else{
            menu(premiumCommand);
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
