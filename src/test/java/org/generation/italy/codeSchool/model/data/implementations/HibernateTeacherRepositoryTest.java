package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.entities.*;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.codeSchool.model.data.implementations.HibernateTestUtils.*;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class HibernateTeacherRepositoryTest {
    private Teacher t1;
    private Teacher t2;
    private Skill s1;
    private Skill s2;
    private Competence co1;
    private Competence co2;
    private Competence co3;
    private Competence co4;
    private Category cat1;
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
        cat1= new Category(0,CATEGORY1_NAME);
        s1 = new Skill(0, SKILL1_NAME, null, cat1);
        s2 = new Skill(0, SKILL2_NAME, null, cat1);
        co1 = new Competence(0, s1, t1, Level.ADVANCED);
        co2 = new Competence(0, s2, t1, Level.INTERMEDIATE);
        co3 = new Competence(0, s1, t2, Level.BASE);
        co4 = new Competence(0, s2, t2, Level.GURU);

        t1.addCompetence(co1);
        t1.addCompetence(co2);
        t2.addCompetence(co3);
        t2.addCompetence(co4);

        session = HibernateUtils.getSessionFactory().openSession();
        session.getTransaction().begin();

        long catId1 = insertEntity(cat1, session);
        long skId1 = insertEntity(s1,session);
        long skId2 = insertEntity(s2,session);
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
            session.clear();
            Iterable<Teacher> it = repo.findWithCompetenceByLevel(Level.ADVANCED);
            List<Teacher> tc = new ArrayList<>();
            it.forEach(tc::add);
            assertEquals(1, tc.size());
            assertEquals(t1.getId(), tc.get(0).getId());
            assertEquals(2, tc.get(0).getCompetences().size());

            Optional<Competence> oc1 =tc.get(0).getCompetences()
                    .stream()
                    .filter(c->c.getId()== co1.getId())
                    .findFirst();
            assertTrue(oc1.isPresent());
            assertEquals(Level.ADVANCED, oc1.get().getLevel());
            assertEquals(SKILL1_NAME, oc1.get().getSkill().getName());

            Optional<Competence> oc2 =tc.get(0).getCompetences()
                    .stream()
                    .filter(c->c.getId()== co2.getId())
                    .findFirst();
            assertTrue(oc2.isPresent());
            assertEquals(Level.INTERMEDIATE, oc2.get().getLevel());
            assertEquals(SKILL2_NAME, oc2.get().getSkill().getName());


        } catch (DataException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findWithSkillAndLevel(){
        session.clear();
        Iterable<Teacher> it =repo.findWithSkillAndLevel(s1.getId(), Level.ADVANCED);
        List<Teacher> tc = new ArrayList<>();
        it.forEach(tc::add);

        assertEquals(1, tc.size());
        assertEquals(t1.getId(), tc.get(0).getId());

        Optional<Competence> competenceFounded = tc.get(0).getCompetences()
                        .stream()
                        .filter(c->c.getLevel() == Level.ADVANCED
                                && c.getSkill().getId()== s1.getId()
                                && c.getSkill().getName().equals(s1.getName()))
                        .findFirst();
        assertTrue(competenceFounded.isPresent());

    }

    @Test
    void findByNEditionModule(){
        session.clear();
        Iterable<Teacher> it =repo.findByNEditionModule(2);
        List<Teacher> tc = new ArrayList<>();
        it.forEach(tc::add);
    }

}