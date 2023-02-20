package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.*;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.DURATION3;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryCourseRepositoryTest {
    private Course c1 = new Course(ID1, TITLE, DESCRIPTION, PROGRAM, DURATION
            , true,LocalDate.of(2000,03,1));
    private Course c2 = new Course(ID2, TITLE2, DESCRIPTION2, PROGRAM2, DURATION2
            , true,LocalDate.of(2001,03,1));
    private Course c3 = new Course(ID3, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3
            , true,LocalDate.of(2002,03,1));
    private Course c4 = new Course(ID1, TITLE, DESCRIPTION, PROGRAM, DURATION
            , true,LocalDate.of(2003,03,1));
    private Course c5 = new Course(ID2, TITLE2, DESCRIPTION2, PROGRAM2, DURATION2
            , true,LocalDate.of(2004,03,1));
    private Course c6 = new Course(ID3, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3
            , true,LocalDate.of(2005,03,1));
    private final InMemoryCourseRepository repo = new InMemoryCourseRepository();

    @BeforeEach
    void setUp() {
        repo.create(c1);
        repo.create(c2);
        repo.create(c3);
        repo.create(c4);
        repo.create(c5);
        repo.create(c6);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getActiveCourses() {
        int x = repo.getActiveCourses();
        assertEquals(3,x);
    }

    @Test
    void deleteNumCourses() {
        repo.deleteNumOldestCourses(2);
    }
}