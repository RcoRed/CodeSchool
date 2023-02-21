package org.generation.italy.codeSchool.model.services.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StandardDidacticServiceTest {
    private CourseRepository repo;
    private StandardDidacticService service;
    private Course c1;
    private Course c2;
    private Course c3;
    private Course c4;
    @BeforeEach
    void setUp() {
        repo = new InMemoryCourseRepository();
        c1 = new Course(1, "Title1", "Description1", "Program1", 200,true, LocalDate.of(2023,02,17));
        c2 = new Course(2, "Title2", "Description2", "Program2", 201,true, LocalDate.of(2023,02,18));
        c3 = new Course(3, "Title3", "Description3", "Program3", 202,true, LocalDate.of(2023,02,16));
        c4 = new Course(4, "Title4", "Description4", "Program4", 203,true, LocalDate.of(2023,02,19));
        try {
            repo.create(c1);
            repo.create(c2);
            repo.create(c3);
            repo.create(c4);
        } catch (DataException e) {
            fail("Errore nell'inserimento corsi del setup: " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findCourseById() {
    }

    @Test
    void findCoursesByTitleContains() {
    }

    @Test
    void saveCourse() {
    }

    @Test
    void updateCourse() {
    }

    @Test
    void deleteCourseById() {
    }

    @Test
    void adjustActiveCourses_should_deactivate_oldest_courses_when_actives_are_more_than_desired() {
        try {
            assertTrue(service.adjustActiveCourses(2));
            assertFalse(c1.isActive());
            assertTrue(c2.isActive());
            assertFalse(c3.isActive());
            assertTrue(c4.isActive());
        } catch (DataException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void adjustActiveCourses_should_return_false_when_actives_already_less_than_desired() {
        try {
            boolean result = service.adjustActiveCourses(4);
            assertFalse(result);
            assertTrue(c1.isActive());
            assertTrue(c2.isActive());
            assertTrue(c3.isActive());
            assertTrue(c4.isActive());
        } catch (DataException e) {
            throw new RuntimeException(e);
        }
    }
}