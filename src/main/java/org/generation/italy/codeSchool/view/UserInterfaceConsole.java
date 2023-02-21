package org.generation.italy.codeSchool.view;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.generation.italy.codeSchool.model.services.implementations.StandardDidacticService;
import com.sun.tools.jconsole.JConsoleContext;
import org.generation.italy.codeSchool.model.services.implementations.StandardDidacticService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterfaceConsole {
//   dovrà parlare solo con StandardDidacticService
   private StandardDidacticService service = new StandardDidacticService(new InMemoryCourseRepository());
   public void start(){
      Scanner scanner = new Scanner(System.in);

      System.out.println();
      char input  = scanner.next().charAt(0);
      switch(input){
         case 'r':
            String partOfTitle = scanner.nextLine().toLowerCase();
            try {
               List<Course> coursesTitleContains = service.findCoursesByTitleContains(partOfTitle);
               for(Course c : coursesTitleContains){
                  System.out.println(c.toString());
               }
            } catch (DataException e) {
               new DataException("Errore nella ricerca del corso. ",e);
            }
            break;
         }

   }
    /*
   Iniezione di dipendenza nella console
   deve avere un metodo Start
   dovrà contenere un menù
   s = salvare un corso
   r= ricercare corso per titolo like
   i = ricerca corso per id
   d = cancellare un corso per id
   u= eseguire update di un corso per id
   j = eliminare il numero di corsi attivi ad un certo numero n
    */

}
