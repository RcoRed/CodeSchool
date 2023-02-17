package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;       //!!importante!!
import static org.generation.italy.codeSchool.model.data.Constants.*;
class CSVFileCourseRepositoryTest {

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
   private static final String CSVLINE1 =String.format(Locale.US,"%d,%s,%s,%s,%.2f",ID,TITLE,DESCRIPTION,PROGRAM,DURATION);
   private static final String CSVLINE2=String.format(Locale.US,"%d,%s,%s,%s,%.2f",ID2,TITLE+TEST,DESCRIPTION+TEST,PROGRAM+TEST,DURATION+1);
   private static final String CSVLINE3=String.format(Locale.US,"%d,%s,%s,%s,%.2f",ID3,TITLE+TEST,DESCRIPTION+TEST,PROGRAM+TEST,DURATION+1);
   private static final String FILENAME="TESTDATA.csv";

   @org.junit.jupiter.api.BeforeEach
   void setUp() throws FileNotFoundException {
      try (PrintWriter pw = new PrintWriter(new FileOutputStream(FILENAME))) {
         pw.println(CSVLINE1);
         pw.println(CSVLINE2);
         pw.println(CSVLINE3);
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
   void findById_finds_course_when_present() {
      Course c1 = new Course(ID,TITLE,DESCRIPTION,PROGRAM,DURATION);
      CSVFileCourseRepository  repo = new CSVFileCourseRepository(FILENAME);
      try{
         Optional<Course> x = repo.findById(ID);
         assertTrue(x.isPresent());
         Course c2 = x.get();
         assertEquals(c1,c2);
      }catch (DataException e) {
         fail("Errore nella ricerca by id sul file di testo" + e.getMessage());
      }
   }

   @Test
   void create() {
      // ARRANGE
      Course c = new Course(ID_CREATE,TITLE,DESCRIPTION,PROGRAM,DURATION);
      CSVFileCourseRepository  repo = new CSVFileCourseRepository(FILENAME);
      // ACT
      try{
         List<String> linesBefore = Files.readAllLines(Paths.get(FILENAME));
         repo.create(c);
         List<String> linesAfter = Files.readAllLines(Paths.get(FILENAME));
         // ASSERT
         assertEquals(linesBefore.size()+1,linesAfter.size());
         String csvLine = linesAfter.get(linesAfter.size()-1);
         String[] tokens = csvLine.split(",");
         assertEquals(ID_CREATE,Long.parseLong(tokens[0]));
         assertEquals(DURATION,Double.parseDouble(tokens[tokens.length-1]));
      }catch (DataException e){
         fail("Fallito il create su file CSV" + e.getMessage());
      }catch (IOException e){
         fail("Fallita la verifica del create su file CSV" + e.getMessage());
      }
   }

   @Test
   void courseToCSV() {
      // ARRANGE      //inizializzo i dati che poi dovrò usare
      Course c = new Course(ID,TITLE,DESCRIPTION,PROGRAM,DURATION);
      CSVFileCourseRepository  repo = new CSVFileCourseRepository(FILENAME);
      // ACT          //richiamo ciò che devo testare
      String csvLine = repo.CourseToCSV(c);
      // ASSERT       //prego che tutto sia andato bene
      //Assertions.assertEquals(1,1);     //possiamo fare assertEquals() perchè l'import è STATIC (quindi evitiamo di scrivere "Assertations." prima)
      assertEquals(CSVLINE1,csvLine);
   }
   @Test
   void deleteById_should_delete_course_when_present() {
      //Arrange
      CSVFileCourseRepository repo = new CSVFileCourseRepository(FILENAME);
      try {
      //ACT
         repo.deleteById(ID);
      //ASSERT
         List<String[]> tokenLines = readTokenizedLines();
         assertTrue(tokenLines.size()==2);
         long courseID = Long.parseLong(tokenLines.get(0)[0]);
         assertEquals(ID+1,courseID);
      } catch (IOException e){
         fail("Errore nell'utilizzo dal file di test CSV");
      }catch (EntityNotFoundException | DataException e){
         fail(ENTITY_NOT_FOUND + e.getMessage());
      }
   }

   @Test
   void deletById_should_throw_when_course_not_present(){
      CSVFileCourseRepository repo = new CSVFileCourseRepository(FILENAME);
      try {
         repo.deleteById(ID_NOT_PRESENT);
         fail("Non viene lanciata EntityNotFoundException quando si cancella un corso non esistente");
      }catch (DataException e) {
         fail("Errore dell'utilizzo del file CSV");
      } catch (EntityNotFoundException e) {
         assertEquals(ENTITY_NOT_FOUND + ID_NOT_PRESENT, e.getMessage());
      }
   }

   @Test
   void betterDeletById_should_throw_when_course_not_present(){
      CSVFileCourseRepository repo = new CSVFileCourseRepository(FILENAME);
      Exception exception = assertThrows(EntityNotFoundException.class, () -> { //LambdaExpression, una funzione anonima
            repo.deleteById(ID_NOT_PRESENT);
         }); //asseriamo che lanceremo un tipo di eccezione di questo tipo
      assertEquals(ENTITY_NOT_FOUND + ID_NOT_PRESENT, exception.getMessage());

   }

   @Test
   void findByTitleContains_should_find_course_if_title_present() throws DataException {
      CSVFileCourseRepository repo = new CSVFileCourseRepository(FILENAME);
      try {
         List<Course> courses = repo.findByTitleContains(TEST);
         assertEquals(2, courses.size());
         for (Course c : courses) {
            assertTrue(c.getId() == ID2 || c.getId() == ID3);
            assertTrue(c.getTitle().contains(TEST));
         }
      } catch(DataException d){
         fail("Errore nella ricerca di corsi per titolo like");
      }
   }

   @Test
   public void update_should_replace_course_when_present(){
      Course c = new Course(ID,TITLE+"lollo", DESCRIPTION,PROGRAM,DURATION);
      CSVFileCourseRepository repo = new CSVFileCourseRepository(FILENAME);
      try{
         List<String> linesBefore = Files.readAllLines(Paths.get(FILENAME));
         repo.update(c);
         List<String> linesAfter = Files.readAllLines(Paths.get(FILENAME));
         assertEquals(linesBefore.size(),linesAfter.size());
         List<String[]> courses = readTokenizedLines();
         for(String[] co : courses){
            if(co[0].equals(Long.toString(ID))){
               assertEquals(TITLE+"lollo",co[1]);
            }
         }
      }catch (DataException e){
         fail("Errore nell'utilizzo del file CSV", e);
      }
      catch (IOException e) {
         fail("Errore nell'utilizzo del file CSV");
      } catch(EntityNotFoundException e){
         fail(ENTITY_NOT_FOUND,e);
      }
   }
   private List<String[]> readTokenizedLines() throws IOException{
      List<String> lines = Files.readAllLines(Paths.get(FILENAME));
      List<String[]>  tokenLines = new ArrayList<>();
      for(String s : lines){
         String[] tokens = s.split(",");
         tokenLines.add(tokens);
      }
      return tokenLines;
   }


}