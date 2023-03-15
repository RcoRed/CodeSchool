package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.entities.Course;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.generation.italy.codeSchool.model.data.implementations.HibernateTestUtils.insertCourse;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.*;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.CREATED_AT;
import static org.junit.jupiter.api.Assertions.*;

class HibernateCourseRepositoryTest {
    private Course c1;
    private Course c2;
    private Course c3;
    private Session s;
    private HibernateCourseRepository repo;

    @BeforeEach
    void setUp() {
        c1 = new Course(0, TITLE1, DESCRIPTION, PROGRAM, DURATION, IS_ACTIVE, CREATED_AT);
        c2 = new Course(0, TITLE2, DESCRIPTION2, PROGRAM2, DURATION2, IS_ACTIVE, CREATED_AT.plusDays(1));
        c3 = new Course(0, TITLE1, DESCRIPTION3, PROGRAM3, DURATION3, IS_ACTIVE, CREATED_AT.plusMonths(2));

        s = HibernateUtils.getSessionFactory().openSession();
        s.getTransaction().begin();

        insertCourse(c1, s);
        insertCourse(c2, s);
        insertCourse(c3, s);

        repo = new HibernateCourseRepository(s);
    }

    @AfterEach
    void tearDown() {
        s.getTransaction().rollback();
        s.close();
    }

    @Test
    void findById() {
        try {
            Optional<Course> c = repo.findById(c1.getId());
            assertFalse(c.isEmpty());
            Course course = c.get();
            assertEquals(c1.getId(), course.getId());
            assertEquals(c1.getTitle(), course.getTitle());
        } catch (DataException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void countActiveCourses() {
    }

    @Test
    void findByTitleContains_should_find_course_when_present() {
        try{
            List<Course> list = repo.findByTitleContains("1_T");
            assertEquals(2, list.size());
            assertTrue(list.get(0).getId() == c1.getId() || list.get(0).getId() == c3.getId());
            assertTrue(list.get(1).getId() == c1.getId() || list.get(1).getId() == c3.getId());
            assertTrue(list.get(0).getTitle().equals(c1.getTitle()) || list.get(0).getTitle().equals(c3.getTitle()));
            assertTrue(list.get(1).getTitle().equals(c1.getTitle()) || list.get(1).getTitle().equals(c3.getTitle()));
        } catch (DataException e){
            fail(e.getMessage());
        }
    }

    @Test
    void deactivateOldest() {
        try{
            repo.deactivateOldest(1);
            assertFalse(c1.isActive());

        } catch (DataException e){
            fail(e.getMessage());
        }
    }

    @Test
    void adjustActiveCourses() {
    }
}