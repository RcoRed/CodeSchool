package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
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
    }

    @AfterEach
    void tearDown() {
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
        }
    }
}