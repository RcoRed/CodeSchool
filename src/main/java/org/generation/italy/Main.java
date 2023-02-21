package org.generation.italy;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.generation.italy.codeSchool.model.services.implementations.StandardDidacticService;
import org.generation.italy.codeSchool.view.UserInterfaceConsole;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        var console= new UserInterfaceConsole(new StandardDidacticService(new InMemoryCourseRepository()));
        console.start();




    }
}