package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.generation.italy.codeSchool.model.data.Constants.*;

class SerializedCourseRepositoryTest {
   private static final long ID=1;
   private static final long ID2=2;
   private static final long ID3=3;
   private static final long ID_NOT_PRESENT = 4;
   private static final long ID_CREATE = 5;
   private static final String TITLE="TITLE";
   private static final String TEST="TEST";
   private static final String DESCRIPTION="DESCRIPTION";
   private static final String PROGRAM="PROGRAM";
   private static final double DURATION=200.0;
   private static final Course COURSE1 = new Course(ID,TITLE,DESCRIPTION,PROGRAM,DURATION, LocalDate.now());
   private static final Course COURSE2=new Course(ID2,TITLE+TEST,DESCRIPTION+TEST,PROGRAM+TEST,DURATION+1,LocalDate.now());
   private static final Course COURSE3=new Course(ID3,TITLE+TEST,DESCRIPTION+TEST,PROGRAM+TEST,DURATION+1,LocalDate.now());
   private static final String FILENAME="TESTDATA.ser";
   private static final SerializedCourseRepository repo = new SerializedCourseRepository(FILENAME);
   @org.junit.jupiter.api.BeforeEach
   void setUp() {
      try (ObjectOutputStream fileOutputS = new ObjectOutputStream(new FileOutputStream(String.valueOf(Paths.get(FILENAME))))) {
         Map<Long,Course> dataSource = new HashMap<>();
         dataSource.put(ID, COURSE1);
         dataSource.put(ID2, COURSE2);
         dataSource.put(ID3, COURSE3);
         fileOutputS.writeObject(dataSource);
//         fileOutputS.close(); No me sirve porque usè un try-with-resoureces
      } catch (IOException e){
         new DataException("Errore nel creare il file",e);
      }
      System.out.println("setUp");
   }

   @org.junit.jupiter.api.AfterEach
   void tearDown() {
      try {
         new FileOutputStream(FILENAME).close();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
      System.out.println("tearDown");
   }
   @Test
   void findById_should_find_by_id_o_no() {

      try{
         SerializedCourseRepository  repo = new SerializedCourseRepository(FILENAME);
         Optional<Course> c = repo.findById(ID);
         assertTrue(c.isPresent());
         Course c2 = c.get();
         assertEquals(COURSE1,c2);
         assertNotEquals(COURSE2,c2);
      }catch (DataException e) {
         fail("Errore nella ricerca by id sul file di testo" + e.getMessage());
      }
   }

   @Test
   void findByTitleContains_a_part() {
      try {
         List<Course> courses_finded = repo.findByTitleContains(TEST); //troveremo i titoli che contengono la parrte "TEST"
         assertEquals(2, courses_finded.size()); //Avendone 2 che lo contengono, vediamo se ne ha salvati 2
         for (Course c : courses_finded) {
            assertTrue(c.getId() == ID2 || c.getId() == ID3);
            assertTrue(c.getTitle().contains(TEST));
         }
      } catch(DataException d){
         fail("Errore nella ricerca di corsi per titolo like");
      }
   }

   @Test
   void create_control_if_created() {
      try (ObjectInputStream fileOutputS = new ObjectInputStream(new FileInputStream(FILENAME))){
         Course newCourse = new Course(ID_CREATE, TITLE,DESCRIPTION,PROGRAM,DURATION,LocalDate.now());
         Map<Long, Course> presentCourses = new HashMap<>();
         presentCourses.putAll((Map<Long,Course>)fileOutputS.readObject());
         long exNumOfCourses = presentCourses.size();
         repo.create(newCourse);
         ObjectInputStream fileOutput = new ObjectInputStream(new FileInputStream(String.valueOf(Paths.get(FILENAME))));
         presentCourses.putAll((Map<Long,Course>)fileOutput.readObject());
         long newNumOfCourses = presentCourses.size();
         assertEquals(exNumOfCourses + 1, newNumOfCourses);
         fileOutput.close();
      } catch (DataException | IOException e) {
         fail(" Failed to create course. ", e);
      } catch (ClassNotFoundException e) {
         new EntityNotFoundException(ENTITY_NOT_FOUND);
      }
   }

   @Test
   void update_replacing_existing_course() {
      try(ObjectInputStream fIS = new ObjectInputStream(new FileInputStream(FILENAME))){
         Course newCourse = new Course(ID, TITLE+TITLE+TEST+TEST,DESCRIPTION,PROGRAM,DURATION,LocalDate.now());
         Map<Long, Course> sexyMap = (Map<Long, Course>) fIS.readObject();
         Map<Long, Course> notSoSexyMap = new HashMap<>(sexyMap);
         //preview situation
         Course oldCourse = sexyMap.get(newCourse.getId());
         long exNumOfCourses = sexyMap.size();
         //updating
         repo.update(newCourse);
         //new situation
         ObjectInputStream fileIS = new ObjectInputStream(new FileInputStream(FILENAME));
         sexyMap = (Map<Long, Course>) fileIS.readObject(); //necessario per leggere l'aggiornamento del file
         fileIS.close();
         long newNumOfCourses = sexyMap.size();
         Course courseUpdated = sexyMap.get(newCourse.getId());
         //asserts
         assertEquals(exNumOfCourses, newNumOfCourses); //controllo che si sìa effettivamente sovrascritto, quindi non variato in num di corsi
         assertNotEquals(oldCourse,courseUpdated);   //Controllo che con quell'id non vi sìa il vecchio corso
         assertEquals(newCourse, courseUpdated);   //controllo che effettivamente il nuovo corso sia quello registrato a quell'id e non qualcosa di strano
         assertNotEquals(sexyMap,notSoSexyMap);
      } catch (FileNotFoundException | EntityNotFoundException | ClassNotFoundException naruto){
         new EntityNotFoundException(ENTITY_NOT_FOUND);
      } catch (IOException | DataException naruto){
         new DataException(naruto.getMessage(), naruto);
      }
   }
   // TRY to fail adding into unknown id
   @Test
   void update_replacing_non_existing_course() {
      try(ObjectInputStream fIS = new ObjectInputStream(new FileInputStream(FILENAME))){
         Course newCourse = new Course(ID_NOT_PRESENT, TITLE+TITLE+TEST+TEST,DESCRIPTION,PROGRAM,DURATION,LocalDate.now());
         Map<Long, Course> sexyMap = (Map<Long, Course>) fIS.readObject();
         Map<Long, Course> notSoSexyMap = new HashMap<>();
         notSoSexyMap.putAll(sexyMap);
         //preview situation
         long exNumOfCourses = sexyMap.size();
         //updating
         repo.update(newCourse);
         //new situation
         ObjectInputStream fileIS = new ObjectInputStream(new FileInputStream(FILENAME));
         sexyMap = (Map<Long, Course>) fileIS.readObject(); //necessario per leggere l'aggiornamento del file
         fileIS.close();
         long newNumOfCourses = sexyMap.size();
         //asserts
         assertEquals(exNumOfCourses, newNumOfCourses); //controllo che si sìa effettivamente sovrascritto, quindi non variato in num di corsi
         assertEquals(sexyMap,notSoSexyMap);
      } catch (FileNotFoundException | EntityNotFoundException | ClassNotFoundException naruto){
         new EntityNotFoundException(ENTITY_NOT_FOUND);
      } catch (IOException | DataException naruto){
         new DataException(naruto.getMessage(), naruto);
      }
   }
   @Test
   void deleteById_duh() {
      try(ObjectInputStream fIS = new ObjectInputStream(new FileInputStream(FILENAME))){
         Map<Long, Course> sexyMap = (Map<Long, Course>) fIS.readObject();
         Map<Long, Course> notSoSexyMap = new HashMap<>(sexyMap);
         long exNumOfCourses = sexyMap.size();
         repo.deleteById(ID);
         ObjectInputStream fileIS = new ObjectInputStream(new FileInputStream(FILENAME));
         sexyMap = (Map<Long, Course>) fileIS.readObject();
         fileIS.close();
         long newNumOfCourses = sexyMap.size();
         assertEquals(exNumOfCourses-1, newNumOfCourses);
         assertNotEquals(notSoSexyMap, sexyMap);
      } catch (FileNotFoundException | ClassNotFoundException | EntityNotFoundException e) {
         new EntityNotFoundException(ENTITY_NOT_FOUND);
      } catch (IOException e) {
         new DataException(e.getMessage(), e);
      } catch (DataException e) {
         throw new RuntimeException(e);
      }
   }
   @Test
   void deleteById_but_don_t_find_it_sad() {
      try(ObjectInputStream fIS = new ObjectInputStream(new FileInputStream(FILENAME))){
         Map<Long, Course> sexyMap = (Map<Long, Course>) fIS.readObject();
         Map<Long, Course> notSoSexyMap = new HashMap<>(sexyMap);
         long exNumOfCourses = sexyMap.size();
         repo.deleteById(ID_NOT_PRESENT);
         ObjectInputStream fileIS = new ObjectInputStream(new FileInputStream(FILENAME));
         sexyMap = (Map<Long, Course>) fileIS.readObject();
         fileIS.close();
         long newNumOfCourses = sexyMap.size();
         assertEquals(exNumOfCourses, newNumOfCourses);
         assertEquals(notSoSexyMap, sexyMap);
      } catch (FileNotFoundException | ClassNotFoundException | EntityNotFoundException e) {
         new EntityNotFoundException(ENTITY_NOT_FOUND);
      } catch (IOException e) {
         new DataException(e.getMessage(), e);
      } catch (DataException e) {
         throw new RuntimeException(e);
      }
   }
}