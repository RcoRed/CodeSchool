package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.*;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.DURATION3;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryCourseEditionRepositoryTest {
    private Course c1 = new Course(ID1, TITLE1, DESCRIPTION, PROGRAM, DURATION, LocalDate.now());
    private Course c2 = new Course(ID2, TITLE2, DESCRIPTION2, PROGRAM2, DURATION2,LocalDate.now());
    private Course c3 = new Course(ID3, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3,LocalDate.now());
    private CourseEdition ce1 = new CourseEdition(1, c1, LocalDate.now(), 163.3);
    private CourseEdition ce2 = new CourseEdition(2, c2, LocalDate.now(), 363.2);
    private CourseEdition ce3 = new CourseEdition(3, c3, LocalDate.now(), 156);
    private static Map<Long, CourseEdition> data = new HashMap<>();
    private InMemoryCourseEditionRepository repo = new InMemoryCourseEditionRepository();
    public InMemoryCourseEditionRepositoryTest(){
        data.put(1L,ce1);
        data.put(2L,ce2);
        data.put(3L,ce3);
    }
    @BeforeEach
    void setUp() throws IOException {
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    void getTotalCost() {
        System.out.println();
    }

    @Test
    void findMostExpensive() {
    }

    @Test
    void findAverageCost() {
    }

    @Test
    void findAllDuration() {
    }

    @Test
    void findByCourse() {
    }

    @Test
    void findByCourseAndTitleAndPeriod() {
    }

    @Test
    void findMedian() {
    }

    @Test
    void findModeByEditionCost() {
    }
}