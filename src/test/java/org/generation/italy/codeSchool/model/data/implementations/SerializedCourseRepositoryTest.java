package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
<<<<<<< HEAD
=======
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
>>>>>>> origin/master
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
<<<<<<< HEAD
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
>>>>>>> origin/master
    }

    @AfterEach
    void tearDown() {
<<<<<<< HEAD
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
        }
    }
>>>>>>> origin/master
}