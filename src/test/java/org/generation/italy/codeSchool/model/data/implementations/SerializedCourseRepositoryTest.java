package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.lang.Object;

import static org.junit.jupiter.api.Assertions.*;

class SerializedCourseRepositoryTest implements Serializable{

    private static final long ID=1;
    private static final long ID2= 2;
    private static final long ID3 = 3;
    private static final long ID_NOT_PRESENT=4;
    private static final long ID_CREATE=5;
    private static final String TEST = "TEST";
    private static final String TITLE="TITLE";
    private static final String DESCRIPTION="DESCRIPTION";
    private static final String PROGRAM="PROGRAM";
    private static final double DURATION=200.0;
    private static final String CSVLINE1=String.format(Locale.US,"%d,%s,%s,%s,%.2f",ID,TITLE,DESCRIPTION,PROGRAM,DURATION);
    private static final String CSVLINE2=String.format(Locale.US,"%d,%s,%s,%s,%.2f",ID2,TITLE+TEST,DESCRIPTION+TEST,PROGRAM+TEST,DURATION+1);
    private static final String CSVLINE3=String.format(Locale.US,"%d,%s,%s,%s,%.2f",ID3,TITLE+TEST+"ciao",DESCRIPTION+TEST,PROGRAM+TEST,DURATION+2);
    private static final String FILENAME="TESTDATA.txt";


    @BeforeEach
    void setUp() throws FileNotFoundException {
        /*try (PrintWriter pw = new PrintWriter(new FileOutputStream(FILENAME))) {
            pw.println(CSVLINE1);
            pw.println(CSVLINE2);
            pw.println(CSVLINE3);
        }*/
    }

    @AfterEach
    void tearDown() {
    }

    /*@Test
    void findById() {
        Course c = new Course(1,"Come spararsi nel corso di java",
                "con la pistola", "sparasi", 123);
        System.out.println("pippo");
        try{
            System.out.println("pippo3");
            FileOutputStream fileOutput = new FileOutputStream("serializzazione.txt");
            ObjectOutputStream output = new ObjectOutputStream(fileOutput);
            output.writeObject(c);
            output.close();
            fileOutput.close();

            System.out.println("pippo4");

            FileInputStream fileInput = new FileInputStream("serializzazione.txt");
            ObjectInputStream input = new ObjectInputStream(fileInput);
            Course c2 = (Course) input.readObject();
            input.close();
            fileInput.close();
            System.out.println("pippo2");

        }catch(IOException e){
            System.out.println("pippo5");
        }catch(ClassNotFoundException e){
            System.out.println("pippo6");
        }
    }*/

    @Test
    void findById_finds_course_when_present() {
        Course c1 = new Course(ID,TITLE,DESCRIPTION,PROGRAM,DURATION);
        SerializedCourseRepository repo = new SerializedCourseRepository(FILENAME);
        try{
            Optional<Course> x = repo.findById(ID);
            assertTrue(x.isPresent());
            Course c2 = x.get();
            assertEquals(c1,c2);
        }catch (DataException e){
            fail("Errore nella ricerca by id sul file di testo" + e.getMessage());
        }
    }

    @Test
    void findByTitleContains_should_find_courses_if_title_present(){
        SerializedCourseRepository repo = new SerializedCourseRepository(FILENAME);
        try{
            List<Course> courses = repo.findByTitleContains(TEST);
            assertEquals(2,courses.size());
            for(Course c : courses){
                assertTrue(c.getId() == ID2 || c.getId() == ID3);
                assertTrue(c.getTitle().contains(TEST));
            }
        }catch (DataException e){
            fail("Errore nella ricerca di corsi per titolo like " + e.getMessage());
        }
    }

   @Test
    void create_should_create() {
        try{
            Course c1 = new Course(ID_CREATE,TITLE,DESCRIPTION,PROGRAM,DURATION);
            SerializedCourseRepository repo = new SerializedCourseRepository(FILENAME);
            repo.create(c1);
            FileInputStream fileInput = new FileInputStream(FILENAME);
            ObjectInputStream input = new ObjectInputStream(fileInput);
            c1 = (Course) input.readObject();
            Course c2= new Course(c1.getId(), c1.getTitle(), c1.getDescription(), c1.getProgram(), c1.getDuration());
            input.close();
            fileInput.close();
            assertEquals(c1,c2);
        }catch (DataException e){
            fail("Fallito il create su file SER" + e.getMessage());
        }catch (FileNotFoundException e){
            fail("File non trovato");
        }catch (IOException e){
            fail("Errore nell'utilizzo del file di testo");
        }catch (ClassNotFoundException e){
            fail("Classe non esistente");
        }
    }

    /*@Test
    void create_should_not_throw(){
        try {
            Course c = new Course(ID_CREATE, TITLE, DESCRIPTION, PROGRAM, DURATION);
            SerializedCourseRepository repo = new SerializedCourseRepository(FILENAME);
            repo.create(c);
        }catch (DataException e){
            fail("Errore nella lettura del file", e);
        }
    }*/


}