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
    private Teacher t1;
    private Teacher t2;
    private Teacher t3;
    private Connection con;
    private JDBCCourseEditionRepository repo;

    @BeforeEach
    void setUp() throws SQLException {
//creazione Course
        c1 = new Course(0, TITLE1,DESCRIPTION,PROGRAM,DURATION,IS_ACTIVE,CREATED_AT);
        c2 = new Course(0, TITLE2,DESCRIPTION2,PROGRAM2,DURATION2,IS_ACTIVE,CREATED_AT.plusDays(1));
//crazione Classroom
        cr1 = new Classroom(0,CLASSROOM_NAME, CLASSROOM_CAPACITY,CLASSROOM_IS_VIRTUAL,CLASSROOM_IS_COMPUTERIZED,
                CLASSROOM_HAS_PROJECTOR, null);
//creazione CourseEdition
        ce1 = new CourseEdition(0,c1,COURSE_EDITION_STARTED_AT,COURSE_EDITION_COST,cr1);
        ce2 = new CourseEdition(0,c1,COURSE_EDITION_STARTED_AT.plusMonths(1),COURSE_EDITION_COST2,cr1);
        ce3 = new CourseEdition(0,c1,COURSE_EDITION_STARTED_AT.plusMonths(2),COURSE_EDITION_COST,cr1);
        ce4 = new CourseEdition(0,c2,COURSE_EDITION_STARTED_AT,COURSE_EDITION_COST,cr1);
//creazione Teacher
        t1 = new Teacher(0,"null","null",CREATED_AT,null,"null",null,null,"null","null",
                null,true,null,null,null,null);
        t2 = new Teacher(0,"null","null",CREATED_AT,null,"null",null,null,"null","null",
                null,true,null,null,null,null);
        t3 = new Teacher(0,"null","null",CREATED_AT,Sex.MALE,"null",null,null,"null","null",
                null,true,null,null,null,null);
//creazione Connection
        con = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
        con.setAutoCommit(false);

//inserimento Course
        int key1 = update(INSERT_COURSE_RETURNING_ID,con,true,c1.getTitle(),
                c1.getDescription(), c1.getProgram(), c1.getDuration(), c1.isActive(),
                Date.valueOf(c1.getCreatedAt()));
        c1.setId(key1);
        int key2 = update(INSERT_COURSE_RETURNING_ID,con,true,c2.getTitle(),
                c2.getDescription(), c2.getProgram(), c2.getDuration(), c2.isActive(),
                Date.valueOf(c2.getCreatedAt()));
        c2.setId(key2);
//inserimento Classroom
        int classroomKey = update(INSERT_CLASSROOM_RETURNING_ID,con,true,
                cr1.getName(),cr1.getMaxCapacity(),cr1.isVirtual(),
                cr1.isComputerized(),cr1.isHasProjector(), null);
        cr1.setId(classroomKey);
//inserimento CourseEdition
        int courseEditionKey1 = update(INSERT_COURSE_EDITION_RETURNING_ID,con,true,
                ce1.getCourse().getId(),ce1.getStartedAt(),ce1.getCost(),cr1.getId());
        ce1.setId(courseEditionKey1);
        int courseEditionKey2 = update(INSERT_COURSE_EDITION_RETURNING_ID,con,true,
                ce2.getCourse().getId(),ce2.getStartedAt(),ce2.getCost(),cr1.getId());
        ce2.setId(courseEditionKey2);
        int courseEditionKey3 = update(INSERT_COURSE_EDITION_RETURNING_ID,con,true,
                ce3.getCourse().getId(),ce3.getStartedAt(),ce3.getCost(),cr1.getId());
        ce3.setId(courseEditionKey3);
        int courseEditionKey4 = update(INSERT_COURSE_EDITION_RETURNING_ID,con,true,
                ce4.getCourse().getId(),ce4.getStartedAt(),ce4.getCost(),cr1.getId());
        ce4.setId(courseEditionKey4);
//inserimento Address
        int id_address1 = update(INSERT_ADDRESS_RETURNING_ID,con,true," ",1," "," ");
//inserimrnto Person
        int id_person1 = update(INSERT_PERSON_RETURNING_ID,con,true,
                t1.getFirstname(),t1.getLastname(),t1.getDob(),Sex.MALE,
                t1.getEmail(),t1.getCellNumber(),id_address1,t1.getUsername(),t1.getPassword());
        int id_person2 = update(INSERT_PERSON_RETURNING_ID,con,true,
                t2.getFirstname(),t2.getLastname(),t2.getDob(),Sex.MALE,
                t2.getEmail(),t2.getCellNumber(),id_address1,t2.getUsername(),t2.getPassword());
        int id_person3 = update(INSERT_PERSON_RETURNING_ID,con,true,
                t3.getFirstname(),t3.getLastname(),t3.getDob(),Sex.MALE,
                t3.getEmail(),t3.getCellNumber(),id_address1,t3.getUsername(),t3.getPassword());
//inserimento Teacher
        int teacherKey1 = update(INSERT_TEACHER_RETURNING_ID,con,true,
                t1.getpIVA(),t1.isEmployee(),t1.getHireDate(),t1.getFireDate(),
                Level.GURU,id_person1);
        t1.setId(teacherKey1);
        int teacherKey2 = update(INSERT_TEACHER_RETURNING_ID,con,true,
                t2.getpIVA(),t2.isEmployee(),t2.getHireDate(),t2.getFireDate(),
                Level.GURU,id_person2);
        t2.setId(teacherKey2);
        int teacherKey3 = update(INSERT_TEACHER_RETURNING_ID,con,true,
                t3.getpIVA(),t3.isEmployee(),t3.getHireDate(),t3.getFireDate(),
                Level.GURU,id_person3);
        t3.setId(teacherKey3);
//inserimento CourseModule
        int id_courseModule = update(INSERT_COURSE_MODULE_RETURNING_ID,con,true,
                " "," ",c1.getId(),2,Level.GURU);
//inserimento EditionModule
        update(INSERT_EDITION_MODULE_RETURNING_ID,con,true,
                id_courseModule,ce1.getId(),t1.getId(),null,null);
        update(INSERT_EDITION_MODULE_RETURNING_ID,con,true,
                id_courseModule,ce1.getId(),t1.getId(),null,null);
        update(INSERT_EDITION_MODULE_RETURNING_ID,con,true,
                id_courseModule,ce2.getId(),t2.getId(),null,null);
        update(INSERT_EDITION_MODULE_RETURNING_ID,con,true,
                id_courseModule,ce2.getId(),t3.getId(),null,null);

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
    void find_by_teacher_id_should_find_editions_when_present() {
        try {
            Iterable<CourseEdition> result = repo.findByTeacherId(t1.getId());
            var it = result.iterator();
            assertTrue(it.hasNext());
            var edition1 = it.next();
            assertEquals(edition1.getId(), ce1.getId());
            assertTrue(it.hasNext());
            var edition2 = it.next();
            assertEquals(edition2.getId(), ce1.getId());
        } catch (DataException e) {
            fail(e.getMessage());
        }
    }
}