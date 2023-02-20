package org.generation.italy.codeSchool.model.services.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.implementations.CSVFileCourseRepository;
import org.generation.italy.codeSchool.model.data.implementations.InMemoryCourseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.*;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.DURATION3;
import static org.junit.jupiter.api.Assertions.*;

class StandardDidacticServiceTest {
    private Course c1 = new Course(ID1, TITLE, DESCRIPTION, PROGRAM, DURATION
            , true, LocalDate.of(2000,03,1));
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
    private final StandardDidacticService repo = new StandardDidacticService(new InMemoryCourseRepository());
    @BeforeEach
    void setUp() {
        try {
            repo.saveCourse(c1);
            repo.saveCourse(c2);
            repo.saveCourse(c3);
            repo.saveCourse(c4);
            repo.saveCourse(c5);
            repo.saveCourse(c6);
        } catch (DataException e) {
            fail("Errore nel caricare i corsi" + e.getMessage());
        }

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void adjustActiveCourses() {
        try {
            repo.adjustActiveCourses(6);
        } catch (DataException e) {
            throw new RuntimeException(e);
        }
    }
}