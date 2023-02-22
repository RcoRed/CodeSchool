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
import org.generation.italy.codeSchool.model.data.services.abstractions.AbstractDidacticService;

import java.time.LocalDate;
import java.util.*;

public class UserInterfaceConsole {
    private Scanner sc = new Scanner(System.in);
    private String askId = "Inserisci id corso: ";
    private String askTitle = "Inserisci il titolo del corso: ";
    private String askDescription = "Inserisci la descrizione del corso: ";
    private String askProgram = "Inserisci il programma del corso: ";
    private String askDuration = "Inserisci la durata del corso: ";
    private String askActive = "Inserisci se il corso è attivo: "; //si o no
    private String askCreateAt = "Inserisci la data di creazione del corso: "; //yy-mm-dd
    private String password = "java";
    private AbstractDidacticService service;
    public UserInterfaceConsole(AbstractDidacticService service) {
        this.service = service;
    }
    public void start(){
        System.out.println("Buongiorno benvenuto nel sistema di gestione corsi.");
        putPassword();
    }
    public String askCommand(String prompt){
        System.out.println(prompt);
        return sc.nextLine();
    }
    public void putPassword(){
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
    public void menu(String command){
        try {
            if (command.equalsIgnoreCase("i")) {
                String ss = "Puoi ora cercare un corso dal suo id: ";
                long idToSearch = readLong(askCommand(ss));
                System.out.println(service.findCourseById(idToSearch));
            } else if (command.equalsIgnoreCase("r")) {
                String ss = "Puoi ora cercare se una stringa di testo è contenuta nel titolo di un corso, inserisci la stringa: ";
                String titleToSearch = askCommand(ss);
                System.out.println(service.findCoursesByTitleContains(titleToSearch).toString());
            } else if (command.equalsIgnoreCase("stop")) {
                System.out.println("Grazie per aver usato il nostro servizio di gestione corsi, arrivederci!");
                System.exit(0);
            } else {
                System.out.println("Il comando inserito non esiste.");
            }
        }catch (DataException e){
            System.out.println("Non è stato trovato ciò che si cercava.");
        }
    }

    public void menuPremium(String premiumCommand) {
        try{
            if (premiumCommand.equalsIgnoreCase("s")) {
                System.out.println("Puoi ora salvare un nuovo corso.");
                service.saveCourse(subMenu());
            } else if (premiumCommand.equalsIgnoreCase("d")) {
                String ss = "Puoi ora cancellare un corso dal suo id: ";
                long idToDelete = readLong(askCommand(ss));
                service.deleteCourseById(idToDelete);
                System.out.println("Il corso è stato eliminato");
            } else if (premiumCommand.equalsIgnoreCase("u")) {
                System.out.println("Puoi ora fare un update ad un corso: ");
                subMenu();
                service.updateCourse(subMenu());
            } else if (premiumCommand.equalsIgnoreCase("j")) {
                System.out.println("Puoi ora controllare i corsi attivi.");
                int numMaxActive = (int) (readInt(askCommand("Inserisci il numero massimo di corsi attivi: ")));
                System.out.println(service.adjustActiveCourses(numMaxActive));
            } else{
                menu(premiumCommand);
            }
        }catch (DataException | EntityNotFoundException e){
            System.out.println("Non è stato trovato ciò che si cercava.");
        }
    }
    public Course subMenu(){
        long id = readLong(askCommand(askId));
        String title = askCommand(askTitle);
        String description = askCommand(askDescription);
        String program = askCommand(askProgram);
        double duration = readDouble(askCommand(askDuration));
        boolean isActive = readBoolean(askCommand(askActive));
        LocalDate createAt = LocalDate.parse(askCommand(askCreateAt));

        return new Course(id,title,description,program,duration,isActive,createAt);
    }

    public double readDouble(String s){
        do {
            System.out.println(s + " ");
            String s1 = sc.nextLine();
            try{
                return Double.parseDouble(s1);
            }catch (NumberFormatException e){
                System.out.println("Formato inserito non valido");
            }
        }while (true);
    }

    public boolean readBoolean(String s){
        do {
            System.out.println(s +" ");
            String s1 =sc.nextLine();
            if (s1.equalsIgnoreCase("si")){
                return true;
            } else if (s1.equalsIgnoreCase("no")) {
                return false;
            }else {
                System.out.println("Devi inserire si o no");
            }
        }while (true);
    }

    public long readLong(String s){
        do {
            System.out.println(s + " ");
            String s1 = sc.nextLine();
            try{
                return Long.parseLong(s1);
            }catch (NumberFormatException e){
                System.out.println("Formato inserito non valido");
            }
        }while (true);
    }
    public long readInt(String s){
        do {
            System.out.println(s + " ");
            String s1 = sc.nextLine();
            try{
                return Integer.parseInt(s1);
            }catch (NumberFormatException e){
                System.out.println("Formato inserito non valido");
            }
        }while (true);
    }
}
