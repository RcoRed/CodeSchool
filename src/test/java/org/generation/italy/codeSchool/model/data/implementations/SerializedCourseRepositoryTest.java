package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;       //!!importante!!
import static org.generation.italy.codeSchool.model.data.Constants.*;
public class SerializedCourseRepositoryTest {

    private static final String FILENAME = "TEST_DATA_SER.ser";
    private static final long ID = 1;
    private static final String TEST = "TEST";
    private static final String TITLE = "TITLE";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String PROGRAM = "PROGRAM";
    private static final double DURATION = 200.0;
    private static final LocalDate NOW = LocalDate.now();
    @BeforeEach
    void setUp() throws DataException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            ArrayList<Course> input = new ArrayList<>();
            Course DUMMY1 = new Course(ID, TITLE, DESCRIPTION, PROGRAM, DURATION, NOW);
            Course DUMMY2 = new Course(ID+1, TITLE+TEST, DESCRIPTION+TEST, PROGRAM+TEST, DURATION+1, NOW);
            Course DUMMY3 = new Course(ID+2, TITLE+TEST+3, DESCRIPTION+TEST+3, PROGRAM+TEST+3, DURATION+2, NOW);
            input.add(DUMMY1);
            input.add(DUMMY2);
            input.add(DUMMY3);
            outputStream.writeObject(input);
        } catch (IOException e) {
            throw new DataException("Errore nell'interazione col file", e);
        }

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findById_should_find_a_course_if_present() throws DataException {
        try {
            Course c = new Course(ID+1, TITLE+TEST, DESCRIPTION+TEST, PROGRAM+TEST, DURATION+1, NOW);
            SerializedCourseRepository repo = new SerializedCourseRepository(FILENAME);
            Optional<Course> found = repo.findById(ID+1);
            assertTrue(found.isPresent());
            Course foundCourse = found.get();
            assertEquals(c, foundCourse);
            System.out.println(c);
            System.out.println(Optional.of(found));
        } catch (DataException | FileNotFoundException de) {
            throw new DataException("Error", de);
        }
    }

    @Test
    void create_should_add_new_course() throws DataException, FileNotFoundException {
        Course c = new Course(27, "PIPO", "PIPO È BELLO", "WOW, SUCH PROGRAM", 20.0, NOW);
        SerializedCourseRepository repo = new SerializedCourseRepository(FILENAME);
        repo.create(c);
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(FILENAME))){
            ArrayList<Course> readInput = (ArrayList<Course>) input.readObject();
            assertEquals(4, readInput.size());
            assertEquals(27, readInput.get(readInput.size() - 1).getId());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new DataException("error", e);
        }
    }
}
