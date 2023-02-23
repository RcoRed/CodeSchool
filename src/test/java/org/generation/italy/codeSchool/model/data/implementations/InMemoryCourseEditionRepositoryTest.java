package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryCourseEditionRepositoryTest {

    InMemoryCourseEditionRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryCourseEditionRepository();
        Course cc1 = new Course(ID1, TITLE, DESCRIPTION, PROGRAM, DURATION, LocalDate.of(2023, 1, 30));
        Course cc2 = new Course(ID2, TITLE2, DESCRIPTION2, PROGRAM2, DURATION2, LocalDate.of(2023, 5, 30));
        Course cc3 = new Course(ID3, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3, LocalDate.of(2023, 9, 1));
        CourseEdition c1 = new CourseEdition(1, cc1, 100);
        CourseEdition c2 = new CourseEdition(2, cc2, 150);
        CourseEdition c3 = new CourseEdition(3, cc3, 100);

        repo.createEdition(c1);
        repo.createEdition(c2);
        repo.createEdition(c3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCourseEditionTotalCost() {
        double sum = repo.getCourseEditionTotalCost();
        assertEquals(350, sum);
        System.out.println(sum);
    }

    @Test
    void getMostExpensiveCourseEdition(){
        Optional<CourseEdition> ce = repo.getMostExpensiveCourseEdition();
        assertTrue(ce.isPresent());
        double sum = ce.get().getCost();
        assertEquals(150, sum);
    }

    @Test
    void getAverageCost(){
        double avg = repo.getAverageCost();
        assertEquals(116.6, avg, 0.1);
    }

    @Test
    void getCourseEditionsDuration(){
        List<Double> list = repo.getCourseEditionsDuration();
        assertEquals(3, list.size());
    }

    @Test
    void getCourseEditionByCourseId(){
        Optional<CourseEdition> list = repo.getCourseEditionByCourseId(3);
        assertTrue(list.isPresent());
    }

    @Test
    void getCourseEditionByCourseByTitleAndDateRange(){
        List<CourseEdition> list = repo.getCourseEditionByCourseByTitleAndDateRange("TIT",
                LocalDate.of(2023, 1, 29),
                LocalDate.of(2023, 6, 30));
        assertEquals(2, list.size());
    }

    @Test
    void getMedianCourseEdition(){
        List<CourseEdition> list = repo.getMedianCourseEdition();
        assertFalse(list.isEmpty());
        assertTrue(list.size() == 1 || list.size() == 2);
    }

    @Test
    void getMode(){
        Optional<CourseEdition> course = repo.getModeCourseEdition();

    }
}