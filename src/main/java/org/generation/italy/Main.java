package org.generation.italy;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.implementations.CSVFileCourseRepository;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.generation.italy.codeSchool.model.services.implementations.StandardDidacticService;
import org.generation.italy.codeSchool.view.UserInterfaceConsole;

import java.time.LocalDate;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws DataException {
        var repo = new InMemoryCourseRepository();
        var service = new StandardDidacticService(repo);
        Course c1 = new Course(1, "Title1", "Description1", "Program1", 200, LocalDate.of(2023,02,19));
        Course c2 = new Course(2, "Title2", "Description2", "Program2", 201, LocalDate.of(2023,02,18));
        Course c3 = new Course(3, "Title3", "Description3", "Program3", 202, LocalDate.of(2023,02,17));
        Course c4 = new Course(4, "Title4", "Description4", "Program4", 203, LocalDate.of(2023,02,16));
        repo.create(c1);
        repo.create(c2);
        repo.create(c3);
        repo.create(c4);
        System.out.println(service.adjustActiveCourses(2));
    }
}