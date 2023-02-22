package org.generation.italy;

import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.generation.italy.codeSchool.model.data.services.implementations.StandardDidacticService;
import org.generation.italy.codeSchool.view.UserInterfaceConsole;

public class Main {
    public static void main(String[] args){       //ricordati di eliminare eventuali THROWS del main!

        //var repo = new InMemoryCourseRepository();
        //var service = new StandardDidacticService(repo); // gli passo il repository su cui service lavora
        var console = new UserInterfaceConsole(new StandardDidacticService(new InMemoryCourseRepository()));
        console.start();

    }
}