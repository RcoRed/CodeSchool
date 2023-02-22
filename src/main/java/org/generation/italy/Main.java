package org.generation.italy;

import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.implementations.CSVFileCourseRepository;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.generation.italy.codeSchool.model.data.implementations.SerializedCourseRepository;
import org.generation.italy.codeSchool.model.data.services.implementations.StandardDidacticService;
import org.generation.italy.codeSchool.view.UserInterfaceConsole;

public class Main {
    public static void main(String[] args){       //ricordati di eliminare eventuali THROWS del main!

        //var repo = new InMemoryCourseRepository();
        //var service = new StandardDidacticService(repo); // gli passo il repository su cui service lavora
        CourseRepository repo;
        if(args.length == 0 || args[0].equalsIgnoreCase("mem")){
            repo = new InMemoryCourseRepository();
            System.out.println(repo.getClass().getName());
        } else if (args[0].equalsIgnoreCase("ser")) {
            repo = new SerializedCourseRepository();
            System.out.println(repo.getClass().getName());
        } else{
            repo = new CSVFileCourseRepository();
            System.out.println(repo.getClass().getName());
        }
        //var console = new UserInterfaceConsole(new StandardDidacticService(new InMemoryCourseRepository()));
        var console = new UserInterfaceConsole(new StandardDidacticService(repo));
        console.start();
    }
}