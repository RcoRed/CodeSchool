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

import static org.junit.jupiter.api.Assertions.*;

class SerializedCourseRepositoryTest {
    private static final long ID=1;
    private static final long ID2=2;
    private static final long ID3=3;
    private static final long ID_NOT_PRESENT=22;
    private static final long ID_CREATE=4;
    private static final String TITLE="TITLE";
    private static final String DESCRIPTION="DESCRIPTION";
    private static final String PROGRAM="PROGRAM";
    private static final double DURATION=200.0;
    private static final String TEST="TEST";
    private static final Course COURSE1 = new Course(ID,TITLE,DESCRIPTION,PROGRAM,DURATION);
    private static final Course COURSE2 = new Course(ID2,TITLE+TEST,DESCRIPTION+TEST,PROGRAM+TEST,DURATION+1);
    private static final Course COURSE3 = new Course(ID3,TITLE+TEST,DESCRIPTION+TEST,PROGRAM+TEST,DURATION+2);
    private static final String FILENAME="TEST_DATA_SER.txt";

    @BeforeEach
    void setUp() throws IOException {
        ArrayList<Course> dataSource = new ArrayList<>();
        dataSource.add(COURSE1);
        dataSource.add(COURSE2);
        dataSource.add(COURSE3);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILENAME))){
            //out.write(3);
            out.writeObject(dataSource);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            new FileOutputStream(FILENAME).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findById_should_finds_course_when_present() {
        // ARRANGE
        Course c1 = new Course(ID, TITLE, DESCRIPTION, PROGRAM, DURATION);
        SerializedCourseRepository repo = new SerializedCourseRepository(FILENAME);
        try {
            // ACT
            Optional<Course> x = repo.findById(ID);
            // ASSERT
            assertTrue(x.isPresent());
            Course c2 = x.get();
            assertEquals(c1, c2);
        } catch (DataException e) {
            fail("Errore nella ricerca by id sul file di testo " + e.getMessage());
        } catch (EntityNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findByTitleContains_should_find_courses_if_title_present(){
        SerializedCourseRepository repo = new SerializedCourseRepository(FILENAME);
        try{
            // ACT
            List<Course> dataSourceAfter = repo.findByTitleContains(TEST);

            assertEquals(2,dataSourceAfter.size());
            for (Course c:dataSourceAfter){
                assertTrue(c.getId() == ID2 || c.getId() == ID3 );
                assertTrue(c.getTitle().contains(TEST));
            }
        }catch (DataException e){
            fail("Errore nella ricerca di corsi per titolo like ", e);
        } catch (EntityNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void create_should_add_course(){
        // ARRANGE
        Course c = new Course(ID_CREATE,TITLE,DESCRIPTION,PROGRAM,DURATION);
        SerializedCourseRepository repo = new SerializedCourseRepository(FILENAME);
        try{
            ArrayList<Course> dataSourceBefore;
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILENAME))){
                    dataSourceBefore = (ArrayList<Course>) in.readObject();
            }

            // ACT
            repo.create(c);

            ArrayList<Course> dataSourceAfter;
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILENAME))){
                    dataSourceAfter = (ArrayList<Course>) in.readObject();
            }
            // ASSERT
            assertEquals(dataSourceBefore.size()+1,dataSourceAfter.size());
            assertEquals(ID_CREATE,dataSourceAfter.get(dataSourceAfter.size()-1).getId());
            assertEquals(DURATION,dataSourceAfter.get(dataSourceAfter.size()-1).getDuration());
        } catch (EntityNotFoundException | ClassNotFoundException e){
            fail(e.getMessage());
        } catch (DataException | IOException e){
            fail("Fallito il create su file SER" + e.getMessage());
        }
    }

    @Test
    void update_should_replace_course_when_present(){
        Course c = new Course(ID2, TITLE+TEST, DESCRIPTION+TEST, PROGRAM+TEST, DURATION+1);
        SerializedCourseRepository repo = new SerializedCourseRepository(FILENAME);
        try{
            ArrayList<Course> dataSourceBefore = new ArrayList<>();
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILENAME))){
                dataSourceBefore = (ArrayList<Course>) in.readObject();
            }

            // ACT
            repo.update(c);

            ArrayList<Course> dataSourceAfter = new ArrayList<>();
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILENAME))){
                dataSourceAfter = (ArrayList<Course>) in.readObject();
            }
            // ASSERT
            assertEquals(dataSourceBefore.size(), dataSourceAfter.size());
            assertEquals(dataSourceAfter.get(2).getId(), c.getId());
            assertEquals(dataSourceAfter.get(2).getTitle(), c.getTitle());
        }catch (EntityNotFoundException | ClassNotFoundException e){
            fail("Errore update, Corso non trovato");
        } catch (DataException | IOException e) {
            fail("Errore update, errore nella ricerca ", e);
        }
    }

    @Test
    void deleteById_should_delete_course_if_present() {
        //ARRANGE
        SerializedCourseRepository repo = new SerializedCourseRepository(FILENAME);
        try{
            ArrayList<Course> dataSourceBefore = new ArrayList<>();
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILENAME))){
                dataSourceBefore = (ArrayList<Course>) in.readObject();
            }

            // ACT
            repo.deleteById(ID);

            ArrayList<Course> dataSourceAfter = new ArrayList<>();
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILENAME))){
                dataSourceAfter = (ArrayList<Course>) in.readObject();
            }
            //ASSERT
            assertEquals(dataSourceBefore.size()-1, dataSourceAfter.size());
            assertEquals(ID2,dataSourceAfter.get(0).getId());

        } catch (EntityNotFoundException | ClassNotFoundException e){
            fail("Errore nella cancellazione di un corso " + e.getMessage());
        } catch (DataException | IOException e){
            fail("Errore nella cancellazione di un corso " + e.getMessage());
        }
    }
    @Test
    void deleteById_should_throw_when_course_not_present() {
        //ARRANGE
        SerializedCourseRepository repo = new SerializedCourseRepository(FILENAME);
        try{
            //ACT
            repo.deleteById(ID_NOT_PRESENT);
            //ASSERT
            fail("Non Viene lanciata EntityNotFound Exception quando si cancella un corso non esistente");
        }catch (EntityNotFoundException e){
            //EXPECTED
        }catch (DataException e){
            fail("Errore nella cancellazione di un corso " + e.getMessage());
        }
    }

//    @Test
//    void superTest() throws DataException{
////        Course c1 = new Course(ID,TITLE,DESCRIPTION,PROGRAM,DURATION);
////        Course c2 = new Course(ID_CREATE,TITLE,DESCRIPTION,PROGRAM,DURATION);
//        //try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILENAME))){
////            out.writeObject(c1);
////            out.writeObject(c2);
////            ArrayList<Course> list = new ArrayList<>();
//            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILENAME))){
//
//                //System.out.println(in.available());
//                Course cc = (Course) in.readObject();
//                //System.out.println(in.available());
//                Course cc2 = (Course) in.readObject();
//                Course cc3 = (Course) in.readObject();
//                Course cc4 = (Course) in.readObject();
//                //System.out.println(in.available());
//                System.out.println(cc);
//                System.out.println(cc2);
//                System.out.println(cc3);
//                System.out.println(cc4);
//
//            } catch (ClassNotFoundException e) {        //quando darà la ClassNotFoundException?
//                System.out.println("class");
//            }catch (IOException e){
//                System.out.println("io");
//            }
//            /*
//            System.out.println(list.get(0).getId());
//            System.out.println(list.get(0).getTitle());
//            System.out.println(list.get(0).getDescription());
//            System.out.println(list.get(0).getProgram());
//            System.out.println(list.get(0).getDuration());
//            System.out.println(list.get(1).getId());
//            System.out.println(list.get(1).getTitle());
//            System.out.println(list.get(1).getDescription());
//            System.out.println(list.get(1).getProgram());
//            System.out.println(list.get(1).getDuration());
//            System.out.println(list.get(1));*/
////        }
////        catch (IOException e) {
////            fail("Fallita la verifica del create su file SER" + e.getMessage());
////        }
//    }
}