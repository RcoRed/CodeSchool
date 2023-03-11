package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.codeSchool.model.data.JDBCConstants.*;
import static org.generation.italy.codeSchool.model.data.implementations.JDBCTestUtils.insertCourse;
import static org.generation.italy.codeSchool.model.data.implementations.JDBCTestUtils.update;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.*;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.CREATED_AT;
import static org.junit.jupiter.api.Assertions.*;

class JDBCCourseEditionRepositoryTest {
    private Course c1;
    private Course c2;
    private Classroom cr1;
    private CourseEdition ce1;
    private CourseEdition ce2;
    private CourseEdition ce3;
    private CourseEdition ce4;
    private CourseModule cm1;
    private EditionModule em1;
    private Teacher t1;
    private Address a1;
    private Skill s1;
    private Competence cp1;
    private Connection con;
    private JDBCCourseEditionRepository repo;

    @BeforeEach
    void setUp() throws SQLException {
        c1 = new Course(0, TITLE1, DESCRIPTION, PROGRAM, DURATION, IS_ACTIVE, CREATED_AT);
        c2 = new Course(0, TITLE2, DESCRIPTION2, PROGRAM2, DURATION2, IS_ACTIVE, CREATED_AT.plusDays(1));

        cr1 = new Classroom(0,CLASSROOM_NAME, CLASSROOM_CAPACITY,CLASSROOM_IS_VIRTUAL,CLASSROOM_IS_COMPUTERIZED,
                CLASSROOM_HAS_PROJECTOR, null);

        ce1 = new CourseEdition(0,c1,COURSE_EDITION_STARTED_AT,COURSE_EDITION_COST,cr1);
        ce2 = new CourseEdition(0,c1,COURSE_EDITION_STARTED_AT.plusMonths(1),COURSE_EDITION_COST2,cr1);
        ce3 = new CourseEdition(0,c1,COURSE_EDITION_STARTED_AT.plusMonths(2),COURSE_EDITION_COST2,cr1);
        ce4 = new CourseEdition(0,c2,COURSE_EDITION_STARTED_AT,COURSE_EDITION_COST2,cr1);

        cm1 = new CourseModule(0, TITLE1, TEST, c1, DURATION, Level.ADVANCED);

        em1 = new EditionModule(0, cm1, t1, LocalDate.now(), LocalDate.now());

        a1 = new Address(0, ADDRESS_STREET, ADDRESS_HOUSE_NUMBER,ADDRESS_CITY,ADDRESS_COUNTRY);

        t1 = new Teacher(0,FIRSTNAME1, LASTNAME1, CREATED_AT, Sex.MALE, EMAIL1, CELL_NUMBER1, a1, USERNAME, PASSWORD_TEST,
                P_IVA1, IS_EMPLOYEE, LocalDate.now(), null, Level.ADVANCED, new HashSet<>());

        cp1 = new Competence(0,s1,t1,Level.ADVANCED);

        t1.getCompetences().add(cp1);

        con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        con.setAutoCommit(false);
        int key1 = update(INSERT_COURSE_RETURNING_ID, con, true,c1.getTitle(),
                c1.getDescription(), c1.getProgram(), c1.getDuration(), c1.isActive(),
                Date.valueOf(c1.getCreatedAt()));
        c1.setId(key1);
        int key2 = update(INSERT_COURSE_RETURNING_ID, con, true,c2.getTitle(),
                c2.getDescription(), c2.getProgram(), c2.getDuration(), c2.isActive(),
                Date.valueOf(c2.getCreatedAt()));
        c2.setId(key2);
        int classroomKey = update(INSERT_CLASSROOM_RETURNING_ID, con, true, cr1.getName(),cr1.getMaxCapacity(),
                cr1.isVirtual(), cr1.isComputerized(), cr1.isHasProjector(), null);
        cr1.setId(classroomKey);
        int courseEditionKey1 = update(INSERT_COURSE_EDITION_RETURNING_ID, con,true,c1.getId(),ce1.getStartedAt(),
                ce1.getCost(), cr1.getId());
        ce1.setId(courseEditionKey1);
        int courseEditionKey2 = update(INSERT_COURSE_EDITION_RETURNING_ID, con,true,c1.getId(),ce2.getStartedAt(),
                ce2.getCost(), cr1.getId());
        ce2.setId(courseEditionKey2);
        int courseEditionKey3 = update(INSERT_COURSE_EDITION_RETURNING_ID, con,true,c1.getId(),ce3.getStartedAt(),
                ce3.getCost(), cr1.getId());
        ce3.setId(courseEditionKey3);
        int courseEditionKey4 = update(INSERT_COURSE_EDITION_RETURNING_ID, con,true,c2.getId(),ce4.getStartedAt(),
                ce4.getCost(), cr1.getId());
        ce4.setId(courseEditionKey4);
        repo= new JDBCCourseEditionRepository(con);
    }

    @AfterEach
    void tearDown() {
        try {
            con.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getTotalCost() {
    }

    @Test
    void find_most_expensive_should_find_edition_when_present() {
        Optional<CourseEdition> expensive = repo.findMostExpensive();
        assertTrue(expensive.isPresent());
        CourseEdition ce = expensive.get();
        assertEquals(ce2.getId(),ce.getId());
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
    void find_by_course_title_and_period_should_find_editions_when_present() {
        try {
            Iterable<CourseEdition> result = repo.findByCourseTitleAndPeriod("1_TE",
                    CREATED_AT,CREATED_AT.plusMonths(1));
            var it = result.iterator();
            assertTrue(it.hasNext());
            var edition1 = it.next();
            assertTrue(edition1.getId() == ce1.getId() || edition1.getId() == ce2.getId());
            assertTrue(it.hasNext());
            var edition2 = it.next();
            assertTrue(edition2.getId() == ce1.getId() || edition2.getId() == ce2.getId());
        } catch (DataException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findMedian() {
    }

    @Test
    void getCourseEditionCostMode() {
    }

    @Test
    void findByTeacherId() {
        try{
            Iterable<CourseEdition> result = repo.findByTeacherId(1);
        } catch (DataException e) {
            throw new RuntimeException(e);
        }
    }
}