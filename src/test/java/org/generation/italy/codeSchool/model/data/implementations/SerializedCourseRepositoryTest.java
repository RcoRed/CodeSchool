package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
<<<<<<< HEAD
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SerializedCourseRepositoryTest {
    private static final long ID=1;
    private static final long ID2= 2;
    private static final long ID3 = 3;
    private static final long ID4=4;
    private static final long ID_CREATE=5;
    private static final String TEST = "TEST";
    private static final String TITLE="TITLE";
    private static final String DESCRIPTION="DESCRIPTION";
    private static final String PROGRAM="PROGRAM";
    private static final double DURATION=200.0;
    private static final String CSVLINE1=String.format(Locale.US,"%d,%s,%s,%s,%.2f",ID,TITLE + "fs",DESCRIPTION,PROGRAM,DURATION);
    private static final String CSVLINE2=String.format(Locale.US,"%d,%s,%s,%s,%.2f",ID2,TITLE+TEST,DESCRIPTION+TEST,PROGRAM+TEST,DURATION+1);
    private static final String CSVLINE3=String.format(Locale.US,"%d,%s,%s,%s,%.2f",ID3,TITLE+TEST,DESCRIPTION+TEST,PROGRAM+TEST,DURATION+2);
    private static final String FILENAME="TESTDATA.txt";

    @BeforeEach
    void setUp() throws IOException {
//        try{
//            Course c1 = new Course(ID,TITLE,DESCRIPTION,PROGRAM,DURATION + 1);
//            Course c2 = new Course(ID2,TITLE + TEST,DESCRIPTION,PROGRAM,DURATION + 2);
//            ArrayList<Course> c = new ArrayList<>();
//            c.add(c1);
//            c.add(c2);
//            FileOutputStream outputStream = new FileOutputStream(FILENAME);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//            objectOutputStream.writeObject(c);
//            objectOutputStream.close();
//            outputStream.close();
//        }catch (IOException e){
//            fail("ciao");
//        }
=======
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
>>>>>>> master
    }

    @AfterEach
    void tearDown() {
<<<<<<< HEAD
    }

    @Test
    void findById() {
        try {
            SerializedCourseRepository ser = new SerializedCourseRepository(FILENAME);
            Optional<Course> x = ser.findById(2);
            assertTrue(x.isPresent());
        } catch (DataException e) {
            fail("Errore nell'utilizzo del file", e);
        }
    }

    @Test
    void findByTitleContains() {
        try {
            String part = "TEST";
            List<Course> found = new ArrayList<>();
            SerializedCourseRepository ser = new SerializedCourseRepository(FILENAME);
            found= ser.findByTitleContains(part);
            assertEquals(1,found.size());
            for(Course c : found){
                assertTrue(c.getTitle().contains(part));
            }
        } catch (DataException e) {
            fail("Errore nell'utilizzo del file", e);
        }
    }

    @Test
    void create_should_not_throw() {
        try {
            Course c1 = new Course(ID,TITLE,DESCRIPTION,PROGRAM,DURATION + 1);
            Course c2 = new Course(ID2,TITLE + TEST,DESCRIPTION,PROGRAM,DURATION + 2);
            Course c4 = new Course(ID3, TITLE, DESCRIPTION, PROGRAM, DURATION + 3);
            SerializedCourseRepository ser = new SerializedCourseRepository(FILENAME);
            ser.create(c1);
            ArrayList<Course> before;
            try(FileInputStream file = new FileInputStream(FILENAME);
                ObjectInputStream input = new ObjectInputStream(file)) {
                before = (ArrayList<Course>) input.readObject();
            }
            ser.create(c2);
            ArrayList<Course> after;
            try(FileInputStream file = new FileInputStream(FILENAME);
                ObjectInputStream input = new ObjectInputStream(file)) {
                after = (ArrayList<Course>) input.readObject();
            }
            assertTrue(before.size() < after.size());
        } catch (FileNotFoundException e) {
            fail("File non trovato");
        } catch (IOException e) {
            fail("Errore nell'utilizzo del file di test txt");
        } catch (ClassNotFoundException e) {
            fail("Errore: classe non trovata");
        } catch (DataException e) {
            fail();
        }
    }

    @Test
    void update() {
        try {
            Course c1 = new Course(ID,TITLE,DESCRIPTION,PROGRAM,DURATION + 6);
            ArrayList<Course> before;
            try(FileInputStream file = new FileInputStream(FILENAME);
                ObjectInputStream input = new ObjectInputStream(file)) {
                before = (ArrayList<Course>) input.readObject();
            }
            SerializedCourseRepository ser = new SerializedCourseRepository(FILENAME);
            ser.update(c1);
            ArrayList<Course> after;
            try(FileInputStream file = new FileInputStream(FILENAME);
                ObjectInputStream input = new ObjectInputStream(file)) {
                after = (ArrayList<Course>) input.readObject();
            }
            Course coursebefore = before.get(0);
            Course courseafter = after.get(0);
            assertTrue(coursebefore.getDuration() != courseafter.getDuration());
            //assertTrue(before.get(0) != after.get(0));
        } catch (DataException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteById() {
        try{
            ArrayList<Course> before;
            try(FileInputStream file = new FileInputStream(FILENAME);
                ObjectInputStream input = new ObjectInputStream(file)) {
                before = (ArrayList<Course>) input.readObject();
            }
            SerializedCourseRepository ser = new SerializedCourseRepository(FILENAME);
            ser.deleteById(2);
            ArrayList<Course> after;
            try(FileInputStream file = new FileInputStream(FILENAME);
                ObjectInputStream input = new ObjectInputStream(file)) {
                after = (ArrayList<Course>) input.readObject();
            }
            assertTrue(before.size() > after.size());
        } catch (DataException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
=======
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
            assertEquals(TITLE, coursesAfter.get(coursesAfter.size()-1).getTitle());
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
            for (var co : courses){
                if (co.getId() == c.getId()){
                    assertEquals(c,co);
                    return;
                }
            }
            fail("Errore nell'update del corso:corso non trovato dopo l'update");
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
    void deleteById_should_delete_when_course_present() {
        try {
            repo.deleteById(ID2);
            var courses = load();
            assertEquals(2,courses.size());
            for (var c:courses){
                assertTrue(c.getId()==ID1 || c.getId()==ID3);
            }
        } catch (EntityNotFoundException e) {
            fail("Errore nella cancellazione del corso: corso non trovato anche se presente:"+ e.getMessage());
        } catch (DataException | IOException | ClassNotFoundException e) {
            fail("Errore nella cancellazione del corso:"+ e.getMessage());
        }


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
>>>>>>> master
        }
    }
}