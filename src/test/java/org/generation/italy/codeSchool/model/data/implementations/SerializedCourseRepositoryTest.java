package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class SerializedCourseRepositoryTest {



    private Course c1 = new Course(ID1, TITLE, DESCRIPTION, PROGRAM, DURATION);
    private Course c2 = new Course(ID2, TITLE2, DESCRIPTION2, PROGRAM2, DURATION2);
    private Course c3 = new Course(ID3, TITLE3, DESCRIPTION3, PROGRAM3, DURATION3);
    private List<Course> courses = new ArrayList<>();
    private SerializedCourseRepository repo = new SerializedCourseRepository(SERIALIZED_TEST_FILE_NAME);

    public SerializedCourseRepositoryTest() {
        courses.add(c1);
        courses.add(c2);
        courses.add(c3);
    }

    @BeforeEach
    void setUp() throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SERIALIZED_TEST_FILE_NAME))) {
            oos.writeObject(courses);
        }
        System.out.println("wrote courses");
    }

    @AfterEach
    void tearDown() {
        try {
            new FileOutputStream(SERIALIZED_TEST_FILE_NAME).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findById_finds_course_when_present()  {
        try {
            Optional<Course> oc = repo.findById(c2.getId());
            assertTrue(oc.isPresent());
            Course found = oc.get();
            assertEquals(c2,found);
        } catch (DataException e) {
            fail("Errore nella ricerca by id sul file serializzato " + e.getMessage());
            e.getCause().printStackTrace();
        }
    }

    @Test
    void findByTitleContains_should_find_courses_if_title_present() {
        try {
            List<Course> courses = repo.findByTitleContains(TEST_TITLE_PART);
            assertEquals(2, courses.size());
            for (Course c : courses) {
                assertTrue(c.getId() == ID2 || c.getId() == ID3);
            }
        } catch (DataException e) {
            fail("Errore nella ricerca per titolo sul file serializzato " + e.getMessage());
        }
    }

    @Test
    void create() {
        try {
            Course c = new Course(0,TITLE,DESCRIPTION,PROGRAM,DURATION);
            var courseBefore = load();
            c = repo.create(c);
            var coursesAfter = load();
            assertEquals(courseBefore.size()+1, coursesAfter.size());
            assertEquals(SerializedCourseRepository.nextID, coursesAfter.get(coursesAfter.size()-1).getId());
        } catch (DataException e) {
            fail("errore nalla creazione del corso nel file serializzato:" + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            fail("errore nella lettura dati da file serializzato nel test:" + e.getMessage());
        }
    }

    @Test
    void update_should_change_course_if_present() {
        try {
            Course c = new Course(ID1,TITLE_UPDATED,DESCRIPTION_UPDATED,PROGRAM,DURATION);
            repo.update(c);
            var courses = load();

        } catch (IOException | ClassNotFoundException e) {
            fail("errore nella lettura/scrittura dati da file serializzato nel test:" + e.getMessage());
        } catch (DataException e) {
            fail("errore nalla creazione del corso nel file serializzato:" + e.getMessage());
        } catch (EntityNotFoundException e) {
            fail("update del corso nel file serializzato non trova il corso quando il corso e' presente:"
                    + e.getMessage());
        }

    }

    @Test
    void deleteById() {
    }

    private List<Course> load() throws IOException, ClassNotFoundException {
        File f = new File(SERIALIZED_TEST_FILE_NAME);
        if (!f.exists()) {
            f.createNewFile();
        }
        if (f.length() == 0) {
            return new ArrayList<>();
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SERIALIZED_TEST_FILE_NAME))) {
            List courseList = (List) ois.readObject();
            return courseList;
        }
    }
}