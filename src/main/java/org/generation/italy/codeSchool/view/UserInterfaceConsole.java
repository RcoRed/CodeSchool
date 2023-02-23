package org.generation.italy.codeSchool.view;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.generation.italy.codeSchool.model.services.implementations.StandardDidacticService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static org.generation.italy.codeSchool.model.data.Constants.*;
public class UserInterfaceConsole {
//   dovrà parlare solo con StandardDidacticService
   private StandardDidacticService service = new StandardDidacticService(new InMemoryCourseRepository());
   private Scanner scanner = new Scanner(System.in);
   private char input = '\0';
   private final char SAVE = 's';
   private final char RESEARCH = 'r';
   private final char DEL_ID = 'd';
   private final char UPDATE = 'u';
   private final char NON_ACTIVES = 'j';
   private final char S_ID = 'i';
   private final char EXIT = 'x';
   private boolean running = true;
   private long id;

   public void start(){
      System.out.print("**********************************************************\n" +
                        "***Benvenuto al sistema di gestione di corsi Generation***\n" +
                        "**********************************************************\n\n");
      while (input == '\0' || input != SAVE || input != RESEARCH || input != DEL_ID || input != UPDATE ||
            input != NON_ACTIVES || input != S_ID || running){
         System.out.print("*Cosa desideri fare?\n" +
               "-Salvare un corso nuovo (s):\n" +
               "-Ricerca corso per id (i):\n" +
               "-Ricercare un corso per parti di titolo contenute (r):\n" +
               "-Cancellare un corso per ID (d):\n" +
               "-Eseguire l'update di un corso (u):\n" +
               "-Eliminare il numero di corsi attivi eccedente ad un numero massimo di corsi attivi, partendo più vecchi (j):\n" +
               "-Exit (x):\n");
         input  = scanner.next().charAt(0);
         menu();
      }
      System.out.print("Hai scelto di: ");
   }
   private void menu(){
      switch(input){
         case RESEARCH:
            System.out.println("*** Ricercare un corso per parti di titolo contenute ***");
            research();
            break;
         case SAVE:
            System.out.println("*** Salvare un corso nuovo ***");
            save();
            break;
         case S_ID:
            System.out.println("*** Ricerca corso per id ***");
            searchId();
            break;
         case DEL_ID:
            System.out.println("*** Cancellare un corso per ID ***");
            delById();
            break;
         case UPDATE:
            System.out.println("*** Eseguire l'update di un corso ***");
            update();
            break;
         case NON_ACTIVES:
            System.out.println("*** Eliminare il numero di corsi attivi eccedente ad un numero massimon\n" +
                  "               di corsi attivi, partendo più vecchi                ***");
            nonActives();
            break;
         case EXIT:
            running = false;
            break;
      }
   }
   private void research(){
      String partOfTitle = scanner.nextLine().toLowerCase();
      try {
         List<Course> coursesTitleContains = service.findCoursesByTitleContains(partOfTitle);
         for(Course c : coursesTitleContains){
            System.out.println(c.toString());
         }
      } catch (DataException e) {
         new DataException("Errore nella ricerca del corso. ",e);
      }
   }
   private void save(){
      try {
         char r;
         boolean correct = false;
         boolean isActive = false;
         String title, description, program,duration;
         System.out.println("Introduci il nome del nuovo corso: ");
         title = scanner.next();
         while(!correct){
            do {
               System.out.println(title + " è il nome corretto?(s/n): ");
               r = scanner.next().charAt(0);
               System.out.println(r);
               System.out.println(r != 's' && r != 'n');
            }while( r!='s'  && r!='n' );
            if(r == 's'){
               correct=true;
            } else {
               System.out.println("Introduci il nome del nuovo corso: ");
               title = scanner.next();
            }
         }
         correct = false;
         r='\0';

         System.out.println("Introduci la descrizione del nuovo corso: ");
         description = scanner.next();
         while(!correct){
            do {
               System.out.println(description + " è la descrizione corretta?(s/n): ");
               r = scanner.next().charAt(0);
            }while(r!='s' && r!='n');
            if(r == 's'){
               correct=true;
            } else {
               System.out.println("Introduci la descrizione del nuovo corso: ");
               description = scanner.next();
            }
         }
         correct = false;
         r='\0';
         System.out.println("Introduci il programma del nuovo corso: ");
         program = scanner.next();
         while(!correct){
            do {
               System.out.println(program + " è programma corretto?(s/n): ");
               r = scanner.next().charAt(0);
            }while(r!='s' && r!='n');
            if(r == 's'){
               correct=true;
            } else {
               System.out.println("Introduci il programma del nuovo corso: ");
               program = scanner.next();
            }
         }
         correct = false;
         r='\0';
         System.out.println("Introduci la durata del nuovo corso: ");
         duration = scanner.next();
         while(!correct){
            do {
               System.out.println(duration + " è la durata corretta?(s/n): ");
               r = scanner.next().charAt(0);
            }while(r!='s' && r!='n');
            if(r == 's'){
               correct=true;
            } else {
               System.out.println("Introduci la durata del nuovo corso: ");
               duration = scanner.next();
            }
         }
         correct = false;
         r='\0';
         System.out.println("Vuoi impostare il corso come attivo?(s/n): ");
         char r1 = scanner.next().charAt(0);
         while(!correct){
            do {
               if(r1 == 's'){
                  System.out.println(title + " verrà impostato come attivo, confermare? (s/n)");
                  r = scanner.next().charAt(0);
                  if (r == 's'){
                     correct = true;
                  }
               } else if (r1 == 'n') {
                  System.out.println(title + " verrà impostato come inattivo, confermare? (s/n)");
                  r = scanner.next().charAt(0);
                  if (r == 's'){
                     correct = true;
                  }
               } else {
                  System.out.println("Vuoi impostare il corso come attivo?(s/n): ");
                  r1 = scanner.next().charAt(0);
               }
            }while(r1!='s' && r1!='n');
         }
         if(r1 == 's'){
            isActive = true; //di default è false, se è 'n' non bisogna fare niente
         }
         Course newCourse = new Course(0,title,description,program,Double.parseDouble(duration),isActive,
               LocalDate.now());
         service.saveCourse(newCourse);
      } catch (DataException e) {
         new DataException("Errore al creare il corso ", e);
      }
   }
   private void searchId(){
      id = scanner.nextLong();
      Optional<Course> find;
      try {
         find = service.findCourseById(id);
         if(find.isEmpty()){
            System.out.printf("Ci dispiace, corso non trovato all'id corrispondente: %d\n",id);
         } else {
            System.out.println("Corso trovato! *** " + find.toString());
         }
      } catch (DataException e) {
         new DataException(e.getMessage(),e);
      }
   }
   private void delById(){
      id = scanner.nextLong();
      try {
         service.deleteCourseById(id);
         if(service.findCourseById(id).isEmpty()){
            System.out.printf("Bene! Corso all'id %d cancellato con successo\n", id);
         } else {
            System.out.println("Qualcosa è andato storto, sembra non essersi cancellato il corso all'id: " + id);
         }
      } catch (EntityNotFoundException e) {
         new EntityNotFoundException(ENTITY_NOT_FOUND);
      } catch (DataException e) {
         new DataException("Data Error x-x ",e);
      }
   }
   private void update(){
      try{
         id = -1;
         char r;
         boolean correct = false;
         boolean isActive = false;
         String title, description, program,duration;
         do {
            System.out.println("Introduci l'id del corso di cui vuoi fare l'update: ");
            id = scanner.nextLong();
         } while(service.findCourseById(id).isEmpty());
         System.out.println("Introduci il nuovo nome del corso: ");
         title = scanner.nextLine();
         while(!correct){
            do {
               System.out.println(title + " è il nome corretto?(s/n): ");
               r = scanner.next().charAt(0);
            }while(r!='s' || r!='n');
            if(r == 's'){
               correct=true;
            } else {
               System.out.println("Introduci il nome nuovo del corso: ");
               title = scanner.nextLine();
            }
         }
         correct = false;

         System.out.println("Introduci la nuova descrizione delcorso: ");
         description = scanner.nextLine();
         while(!correct){
            do {
               System.out.println(description + " è la descrizione corretta?(s/n): ");
               r = scanner.next().charAt(0);
            }while(r!='s' || r!='n');
            if(r == 's'){
               correct=true;
            } else {
               System.out.println("Introduci la nuova descrizione del corso: ");
               description = scanner.nextLine();
            }
         }
         correct = false;

         System.out.println("Introduci il nuovo programma del corso: ");
         program = scanner.nextLine();
         while(!correct){
            do {
               System.out.println(program + " è programma corretto?(s/n): ");
               r = scanner.next().charAt(0);
            }while(r!='s' || r!='n');
            if(r == 's'){
               correct=true;
            } else {
               System.out.println("Introduci il nuovo programma del corso: ");
               program = scanner.nextLine();
            }
         }
         correct = false;

         System.out.println("Introduci la nuova durata del corso: ");
         duration = scanner.nextLine();
         while(!correct){
            do {
               System.out.println(duration + " è la durata corretta?(s/n): ");
               r = scanner.next().charAt(0);
            }while(r!='s' || r!='n');
            if(r == 's'){
               correct=true;
            } else {
               System.out.println("Introduci la nuova durata del corso: ");
               duration = scanner.nextLine();
            }
         }
         correct = false;

         System.out.println("Vuoi impostare il corso come attivo?(s/n): ");
         char r1 = scanner.next().charAt(0);
         while(!correct){
            do {
               if(r1 == 's'){
                  System.out.println(title + " verrà impostato come attivo, confermare? (s/n)");
                  r = scanner.next().charAt(0);
                  if (r == 's'){
                     correct = true;
                  }
               } else if (r1 == 'n') {
                  System.out.println(title + " verrà impostato come inattivo, confermare? (s/n)");
                  r = scanner.next().charAt(0);
                  if (r == 's'){
                     correct = true;
                  }
               } else {
                  System.out.println("Vuoi impostare il corso come attivo?(s/n): ");
                  r1 = scanner.next().charAt(0);
               }
            }while(r1!='s' || r1!='n');
         }
         if(r1 == 's'){
            isActive = true;
         }
         Course newCourse = new Course(id,title,description,program,Double.parseDouble(duration),isActive,
               LocalDate.now());
         service.updateCourse(newCourse);
      } catch (DataException e) {
         new DataException("Errore al creare il corso ", e);
      } catch (EntityNotFoundException e) {
         new EntityNotFoundException(ENTITY_NOT_FOUND);
      }
   }
   private void nonActives(){
      System.out.println("Inserisci quanti corsi vuoi tenere attivi al massimo: ");
      int n = scanner.nextInt();
      if(service.adjustActiveCourses(n)){
         System.out.printf("Bene! Ci sono %d numeri di corsi attivi o anche meno!\n", n);
      }
   }

   public double readDouble(String s){
      do{
         System.out.print(s + " ");
         String s1 = scanner.nextLine();
         try{
            return Double.parseDouble(s1);
         }catch (NumberFormatException e){
            System.out.println("Formato inserito non valido");
         }
      }while (true);
   }

   public boolean readBoolean(String s){
      do{
         System.out.print(s +" ");
         String s1 = scanner.nextLine();
         if(s1.equalsIgnoreCase("s")){
            return true;
         } else if (s1.equalsIgnoreCase("n")){
            return false;
         } else {
            System.out.println("Devi inserire (s/n):");
         }
      } while (true);
   }


   public String readString(String s){
      System.out.println(s +" ");
      return scanner.nextLine();
   }
   public double readLong(String s){
      do{
         System.out.print(s + " ");
         String s1 = scanner.nextLine();
         try{
            return Long.parseLong(s1);
         }catch (NumberFormatException e){
            System.out.println("Formato inserito non valido");
         }
      }while (true);
   }

}
    /*
   Iniezione di dipendenza nella console
   deve avere un metodo Start
   dovrà contenere un menù
   s = salvare un corso nuovo
   r= ricercare corso per titolo like
   i = ricerca corso per id
   d = cancellare un corso per id
   u= eseguire update di un corso per id
   j = eliminare il numero di corsi attivi ad un certo numero n
    */


