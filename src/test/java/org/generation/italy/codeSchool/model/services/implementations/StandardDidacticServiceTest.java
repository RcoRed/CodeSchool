package org.generation.italy.codeSchool.model.services.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.*;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.DURATION3;
import static org.junit.jupiter.api.Assertions.*;

class StandardDidacticServiceTest {
    private Course c1;
    private Course c2;
    private Course c3;
    private Course c4;
    private Course c5;
    private Course c6;
    private StandardDidacticService service;
    private InMemoryCourseRepository repo;
    @BeforeEach
    void setUp() {
        repo = new InMemoryCourseRepository();
        service = new StandardDidacticService(repo);
        try {
            c1 = new Course(ID1, TITLE, DESCRIPTION, PROGRAM, DURATION
                    , true, LocalDate.of(2001,03,1));
            c2 = new Course(ID2, TITLE2, DESCRIPTION2, PROGRAM2, DURATION2
                    , true,LocalDate.of(2002,03,1));
            c3 = new Course(ID2, TITLE2, DESCRIPTION2, PROGRAM2, DURATION2
                    , true,LocalDate.of(2007,03,1));
            c4 = new Course(ID1, TITLE, DESCRIPTION, PROGRAM, DURATION
                    , true,LocalDate.of(2008,03,1));
            c5 = new Course(ID2, TITLE2, DESCRIPTION2, PROGRAM2, DURATION2
                    , false,LocalDate.of(2005,03,1));
            c6 = new Course(ID3, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3
                    , true,LocalDate.of(2006,03,1));
            service.saveCourse(c1);
            service.saveCourse(c2);
            service.saveCourse(c3);
            service.saveCourse(c4);
            service.saveCourse(c5);
            service.saveCourse(c6);
        } catch (DataException e) {
            fail("Errore nel caricare i corsi" + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void adjustActiveCourses_should_return_false_when_actives_already_less_than_desired() {
        try {
            boolean result = service.adjustActiveCourses(5);
            assertFalse(result);
            assertTrue(c1.isActive());
            assertTrue(c2.isActive());
            assertTrue(c3.isActive());
            assertTrue(c4.isActive());
            assertFalse(c5.isActive());
            assertTrue(c6.isActive());
        } catch (DataException e) {
            fail("Errore nel cambiamento di isActive");
        }
    }
    @Test
    void adjustActiveCourses_should_deactivate_oldest_courses() {
        try {
            boolean result = service.adjustActiveCourses(3);
            assertTrue(result);
            assertFalse(c1.isActive());
            assertFalse(c2.isActive());
            assertTrue(c3.isActive());
            assertTrue(c4.isActive());
            assertFalse(c5.isActive());
            assertTrue(c6.isActive());
        } catch (DataException e) {
            fail("Errore nel cambiamento di isActive");
        }
    }
}