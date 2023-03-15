package org.generation.italy;

import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.implementations.CSVFileCourseRepository;
import org.generation.italy.codeSchool.model.data.implementations.HibernateUtils;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.generation.italy.codeSchool.model.data.implementations.SerializedCourseRepository;
import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.services.implementations.StandardDidacticService;
import org.generation.italy.codeSchool.view.UserInterfaceConsole;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.generation.italy.codeSchool.model.data.JDBCConstants.*;

@Configuration
@ComponentScan
public class Main {
    public static void main(String[] args){
//        CourseRepository repo;
//        if(args.length == 0 || args[0].equalsIgnoreCase("mem")){   //avvio di default
//            repo = new InMemoryCourseRepository();
//        }else if(args[0].equalsIgnoreCase("ser")){
//            repo = new SerializedCourseRepository();
//        }else {
//            repo = new CSVFileCourseRepository();
//        }
//        System.out.println(repo.getClass().getName());
//        new UserInterfaceConsole(new StandardDidacticService(repo)).userInteraction();
// questa linra decide che profilo usare per il repostory
        System.setProperty("spring.profiles.active", "jdbc");

        try (var ctx =
                     new AnnotationConfigApplicationContext
                             (Main.class)) {

            var factory = HibernateUtils.getSessionFactory();

            Course c = new Course(3000,"title", "description", "program",
                    200, LocalDate.now());
            var session = factory.openSession();
            session.getTransaction().begin();
            session.save(c);
            session.getTransaction().commit();

//            UserInterfaceConsole console = ctx.getBean(UserInterfaceConsole.class);
//            console.start();
        }
    }

    @Bean
    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    }
}