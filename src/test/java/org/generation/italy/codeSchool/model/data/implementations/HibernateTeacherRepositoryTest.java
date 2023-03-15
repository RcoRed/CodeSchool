package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.entities.Level;
import org.generation.italy.codeSchool.model.entities.Sex;
import org.generation.italy.codeSchool.model.entities.Teacher;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.generation.italy.codeSchool.model.data.implementations.HibernateTestUtils.*;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class HibernateTeacherRepositoryTest {
    private Teacher t1;
    private Teacher t2;
    private Session session;
    private HibernateTeacherRepository repo;
    @BeforeEach
    void setUp() {
        t1 = new Teacher(0, "Riccardo", "Java", LocalDate.of(1970,7,12), Sex.MALE, "riky@wowo.com",
                "398765387563", null, "tech.publica", "streambelli",
                new HashSet<>(), "90345677", true, LocalDate.of(2020,12,2),
                null, Level.ADVANCED);
        t2 = new Teacher(0, TEACHER1_FIRSTNAME, TEACHER1_LASTNAME, TEACHER1_DOB, Sex.MALE, TEACHER1_EMAIL, null,
                null, TEACHER1_USERNAME, TEACHER1_PASSWORD, new HashSet<>(), null, TEACHER1_IS_EMPLOYEE,
                null, null, Level.INTERMEDIATE);
        session = HibernateUtils.getSessionFactory().openSession();
        session.getTransaction().begin();

        long idT1 = insertEntity(t1, session);
        long idT2 = insertEntity(t2, session);

        repo = new HibernateTeacherRepository(session);
    }

    @AfterEach
    void tearDown() {
        if(session!=null) {
            session.getTransaction().rollback();
            session.close();
        }
    }

    @Test
    void findWithCompetenceByLevel() {
        try {
            Iterable<Teacher> it = repo.findWithCompetenceByLevel(Level.INTERMEDIATE);
            List<Teacher> tc = new ArrayList<>();
            it.forEach(tc::add);
            assertEquals(1, tc.size());
            assertEquals(t2.getId(), tc.get(0).getId());
        } catch (DataException e) {
            fail(e.getMessage());
        }
    }
}