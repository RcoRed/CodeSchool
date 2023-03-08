package org.generation.italy;

import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.implementations.CSVFileCourseRepository;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.generation.italy.codeSchool.model.data.implementations.JDBCCourseRepository;
import org.generation.italy.codeSchool.model.data.implementations.SerializedCourseRepository;
import org.generation.italy.codeSchool.model.services.implementations.StandardDidacticService;
import org.generation.italy.codeSchool.view.UserInterfaceConsole;

public class Main {
    public static void main(String[] args){
        CourseRepository repo;
        if(args.length == 0 || args[0].equalsIgnoreCase("mem")){   //avvio di default
            repo = new InMemoryCourseRepository();
        }else if(args[0].equalsIgnoreCase("ser")){
            repo = new SerializedCourseRepository();
        }else if(args[0].equalsIgnoreCase("csv")){
            repo = new CSVFileCourseRepository();
        } else {
            repo = new JDBCCourseRepository();
        }
        System.out.println(repo.getClass().getName());
        new UserInterfaceConsole(new StandardDidacticService(repo)).userInteraction();
    }
}