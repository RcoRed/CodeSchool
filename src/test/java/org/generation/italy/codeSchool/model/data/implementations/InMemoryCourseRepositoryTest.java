package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.entities.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.*;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.DURATION3;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryCourseRepositoryTest {
    private Course c1 = new Course(ID1, TITLE1, DESCRIPTION, PROGRAM, DURATION, true, LocalDate.now());
    private Course c2 = new Course(ID2, TITLE2, DESCRIPTION2, PROGRAM2, DURATION2,true, LocalDate.now().minusDays(20));
    private Course c3 = new Course(ID3, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3,true,LocalDate.now().minusDays(389));
    private Course c4 = new Course(ID3+1, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3,true,LocalDate.now().minusDays(3));
    private Course c5 = new Course(ID3+2, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3,true,LocalDate.now().minusDays(4));
    private Course c6 = new Course(ID3+3, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3,true,LocalDate.now().minusDays(24));
    private Course c7 = new Course(ID3+4, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3,true,LocalDate.now().minusDays(55));
    private Course c8 = new Course(ID3+5, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3,true,LocalDate.now().minusDays(22));
    private Course c9 = new Course(ID3+6, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3,true,LocalDate.now().minusDays(224));
    private Course c10 = new Course(ID3+7, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3,true,LocalDate.now().minusDays(52));
    private Course c11 = new Course(ID3+8, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3,true,LocalDate.now().minusDays(45));
    private Course c12 = new Course(ID3+9, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3,true,LocalDate.now().minusDays(64));
    private List<Course> courses = new ArrayList<>();
    private static final String FILE_NAME="ActiveCourses,txt";
    private InMemoryCourseRepository repo = new InMemoryCourseRepository();


    public InMemoryCourseRepositoryTest() {
        courses.add(c1);
        courses.add(c2);
        courses.add(c3);
        courses.add(c4);
        courses.add(c5);
        courses.add(c6);
        courses.add(c7);
        courses.add(c8);
        courses.add(c9);
        courses.add(c10);
        courses.add(c11);
        courses.add(c12);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void cancelOldActiveCourses() {
        repo.deactivateOldest( 2);
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(FILE_NAME))) {
            pw.println(courses);
        }catch (FileNotFoundException e){
            fail("File non trovato");
        }
    }
}