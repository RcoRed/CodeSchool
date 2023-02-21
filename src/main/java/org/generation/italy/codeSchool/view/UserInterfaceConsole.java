package org.generation.italy.codeSchool.view;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.generation.italy.codeSchool.model.services.implementations.StandardDidacticService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterfaceConsole {
    // Riempiamo la classe UserInterfaceConsole.
    //Questa dovrà parlare solo con StandardDidacticService.
     private StandardDidacticService service = new StandardDidacticService(new InMemoryCourseRepository());
     public void start (){
         Scanner scanner = new Scanner(System.in);
         char input  = scanner.next().charAt(0);

         switch (input){
             case 'r' :
                 String partOfTitle = scanner.nextLine().toLowerCase();
                 try {
                     List<Course> result = service.findCoursesByTitleContains(partOfTitle);
                     for (Course c : result){
                         System.out.println(c.toString());
                     }
                 } catch (DataException e) {
                     new DataException("Errore nella ricerca del corso",e);
                 }
                 break;
             case 'i' :

         }
     }

    //Dovremo fare dipendencies injection.
    // Il metodo start dovrà accettare un input dall’utente:
    // - r: ricerca per titolo
    //- i: ricerca per id
    //- d: elimina per id
    //- dammi l’id che vuoi cancellare
    //- u: aggiorna un corso esistente
    //- s: salva un nuovo corso
    //- richiedi di inserire a uno a uno ogni campo
    //- j: limita il numero di corsi attivi a n


}
