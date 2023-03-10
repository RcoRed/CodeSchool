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
import java.util.Optional;

import static org.generation.italy.codeSchool.model.data.JDBCConstants.*;
import static org.generation.italy.codeSchool.model.data.implementations.JDBCTestUtils.update;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.*;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.TEACHER_1_PASSWORD;
import static org.junit.jupiter.api.Assertions.*;

class JDBCCourseEditionRepositoryTest {
    private Course c1;
    private Course c2;
    private Classroom cr1;
    private CourseEdition ce1;
    private CourseEdition ce2;
    private CourseEdition ce3;
    private CourseEdition ce4;
    private EditionModule em1;
    private EditionModule em2;
    private EditionModule em3;

    private Teacher t1;
    private Teacher t2;
    private Category cat1;
    private Competence co1;
    private Competence co2;
    private Address a1;
    private Connection con;
    private JDBCCourseEditionRepository repo;

    @BeforeEach
    void setUp() throws SQLException {
        //courses
        c1 = new Course(0, TITLE1, DESCRIPTION, PROGRAM, DURATION, IS_ACTIVE, CREATED_AT);
        c2 = new Course(0, TITLE2, DESCRIPTION2, PROGRAM2, DURATION2, IS_ACTIVE, CREATED_AT.plusDays(1));
        //Classrooms
        cr1 = new Classroom(0,CLASSROOM_NAME, CLASSROOM_CAPACITY,CLASSROOM_IS_VIRTUAL,CLASSROOM_IS_COMPUTERIZED,
                CLASSROOM_HAS_PROJECTOR, null);
        //Course Editions
        ce1 = new CourseEdition(0,c1,COURSE_EDITION_STARTED_AT,COURSE_EDITION_COST,cr1);
        ce2 = new CourseEdition(0,c1,COURSE_EDITION_STARTED_AT.plusMonths(1),COURSE_EDITION_COST2,cr1);
        ce3 = new CourseEdition(0,c1,COURSE_EDITION_STARTED_AT.plusMonths(2),COURSE_EDITION_COST2,cr1);
        ce4 = new CourseEdition(0,c2,COURSE_EDITION_STARTED_AT,COURSE_EDITION_COST2,cr1);
        a1 = new Address();
        //Teachers + cometence
        t1 = new Teacher(0, "Riccardo", "Java", LocalDate.of(1970,7,12), Sex.MALE, "riky@wowo.com",
                "398765387563", a1, "tech.publica", "streambelli",
                new HashSet<>(), "90345677", true, LocalDate.of(2020,12,2),
                null, Level.ADVANCED);
        co1 = new Competence(0,new Skill (0, SKILL_1_NAME, null), t1, Level.ADVANCED);
        t1.addCompetence(co1);

        t2 = new Teacher(0, TEACHER_1_FIRSTNAME, TEACHER_1_LASTNAME, TEACHER_1_DOB, Sex.MALE, TEACHER_1_EMAIL,
              null, null, TEACHER_1_USERNAME, TEACHER_1_PASSWORD,
              new HashSet<>(), null, TEACHER_1_IS_EMPLOYEE, null,
              null, Level.ADVANCED);
        co2 = new Competence(0, new Skill(0, SKILL_2_NAME, null), t1, Level.ADVANCED);
        t2.addCompetence(co2);
        //Edition Module
        em1 = new EditionModule(0, null, t1, null, null);
        em2 = new EditionModule(0, null, t2, null, null);
        em3 = new EditionModule(0, null, t1, null, null);
        //category

        cat1 = new Category(0, CATEGORY_1_NAME);
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
        int categoryKey1 = update(INSERT_CATEGORY_RETURNING_ID, con, true, cat1.getName());
        cat1.setId(categoryKey1);
        int SkillKey1 = update(INSERT_SKILL_RETURNING_ID, con, true, co1.getSkill().getName());
        co1.getSkill().setId(SkillKey1);
        int SkillKey2 = update(INSERT_SKILL_RETURNING_ID, con, true, co2.getSkill().getName());
        co2.getSkill().setId(SkillKey2);
        int personKey1 = update(INSERT_PERSON_RETURNING_ID, con, true, t1.getFirstname(),
              t1.getLastname(), t1.getDob(), t1.getSex(), t1.getEmail(), t1.getUsername(), t1.getPassword());
        t1.setId(personKey1);
        int personKey2 = update(INSERT_PERSON_RETURNING_ID, con, true, t2.getFirstname(),
              t2.getLastname(), t2.getDob(), t2.getSex(), t2.getEmail(), t2.getUsername(), t2.getPassword());
        t2.setId(personKey2);
        update(INSERT_TEACHER, con, false, personKey1, t1.getpIVA(), t1.isEmployee(),
                                    t1.getLevel());
        update(INSERT_TEACHER, con, false, personKey2, t2.getpIVA(), t2.isEmployee(),
              t2.getLevel());
        int competenceKey1 = update(INSERT_COMPETENCE_RETURNING_ID, con, true, t1.getId(),
                                    co1.getSkill().getId(), co1.getLevel());
        co1.setId(competenceKey1);
        int competenceKey2 = update(INSERT_COMPETENCE_RETURNING_ID, con, true, t2.getId(),
              co2.getSkill().getId(), co2.getLevel());
        co2.setId(competenceKey2);
        int editionModuleKey1 = update(INSERT_EDITION_MODULE_RETURNING_ID, con, true, ce1.getId(),
              t1.getId());
        em1.setId(editionModuleKey1);
        int editionModuleKey2 = update(INSERT_EDITION_MODULE_RETURNING_ID, con, true, ce2.getId(),
              t2.getId());
        em1.setId(editionModuleKey2);
        int editionModuleKey3 = update(INSERT_EDITION_MODULE_RETURNING_ID, con, true, ce3.getId(),
              t1.getId());
        em3.setId(editionModuleKey3);

        repo = new JDBCCourseEditionRepository(con);
    }

    @AfterEach
    void tearDown() {
        try {
            if(con != null){
                con.rollback();
            }
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
        Iterable<CourseEdition> result = repo.findByCourseTitleAndPeriod("1_TE",
                CREATED_AT,CREATED_AT.plusMonths(1));
        var it = result.iterator();
        assertTrue(it.hasNext());
        var edition1 = it.next();
        assertTrue(edition1.getId() == ce1.getId() || edition1.getId() == ce2.getId());
        assertTrue(it.hasNext());
        var edition2 = it.next();
        assertTrue(edition2.getId() == ce1.getId() || edition2.getId() == ce2.getId());
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
            Iterable<CourseEdition> ceList = repo.findByTeacherId(t1.getId());
            var it = ceList.iterator();
            assertTrue(it.hasNext());
            var courseEdition1_1 = it.next();
            assertTrue(courseEdition1_1.getId() == ce1.getId() || courseEdition1_1.getId() == ce3.getId()  );
            assertTrue(it.hasNext());
            assertNotNull(courseEdition1_1.getCourse());
            assertEquals(c1.getTitle(),courseEdition1_1.getCourse().getTitle());
            assertEquals(cr1.getName(), courseEdition1_1.getAssignedClassRoom().getName());
            var courseEdition2_2 = it.next();
            assertTrue(courseEdition2_2.getId() == ce1.getId() || courseEdition2_2.getId() == ce3.getId()  );
            assertNotNull(courseEdition2_2.getCourse());
            assertEquals(c1.getTitle(),courseEdition2_2.getCourse().getTitle());
            assertEquals(cr1.getName(), courseEdition2_2.getAssignedClassRoom().getName());
        } catch (DataException e){
            fail(e.getMessage());
        }
    }
}