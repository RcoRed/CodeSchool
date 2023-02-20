package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.io.*;

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
    private static final String CSVLINE3=String.format(Locale.US,"%d,%s,%s,%s,%.2f",ID3,TITLE+TEST,DESCRIPTION+TEST,PROGRAM+TEST,DURATION+2);
    private static final String FILENAME="TESTDATA.csv";

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(FILENAME))) {
            pw.println(CSVLINE1);
            pw.println(CSVLINE2);
            pw.println(CSVLINE3);
        }
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        try {
            new FileOutputStream(FILENAME).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findById_finds_course_when_present() {
        Course c1 = new Course(ID,TITLE,DESCRIPTION,PROGRAM,DURATION);
        CSVFileCourseRepository  repo = new CSVFileCourseRepository(FILENAME);
        try{                                                                 //obbligo a scrivere subito
            Optional<Course> x = repo.findById(ID);
            assertTrue(x.isPresent());
            Course c2 = x.get();
            assertEquals(c1,c2);
        }catch (DataException e){
            fail("Errore nella ricerca by id sul file di testo" + e.getMessage());
        }
    }
    @Test
    void findById_should_not_throw_when_file_not_exist(){
        Course c1 = new Course(ID,TITLE,DESCRIPTION,PROGRAM,DURATION);
        CSVFileCourseRepository  repo = new CSVFileCourseRepository(FILENAME);
        try{                                                //obbligo a scrivere subito
            File f = new File(FILENAME);
            assertTrue(f.delete());
            Optional<Course> x = repo.findById(ID);
            assertFalse(x.isPresent());
        }catch (DataException e){
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
    void create_should_save_even_if_file_not_exist(){
        try{
            Course c = new Course(ID_CREATE,TITLE,DESCRIPTION,PROGRAM,DURATION);
            CSVFileCourseRepository  repo = new CSVFileCourseRepository(FILENAME);
            File f = new File(FILENAME);
            assertTrue(f.delete());
            repo.create(c);
            List<String> linesAfter = Files.readAllLines(Paths.get(FILENAME));
            // ASSERT
            assertEquals(1,linesAfter.size());
            String csvLine = linesAfter.get(0);
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
    void deleteById_should_delete_course_when_present(){
        //Arrange
        CSVFileCourseRepository repo = new CSVFileCourseRepository(FILENAME);
        //arrange
        try {
            //Act
            List<String> linesBefore = Files.readAllLines(Paths.get(FILENAME));
            repo.deleteById(ID);
            //Assert
            List<String[]> tokenLines = readTokenizedLines();
            assertEquals(linesBefore.size() - 1, tokenLines.size());
            long courseId = Long.parseLong(tokenLines.get(0)[0]);
            assertEquals(ID2,courseId);
        }catch (IOException e){
            fail("Errore nell'utilizzo del file di test CSV");
        }catch (EntityNotFoundException | DataException e){
            fail("Errore nella cancellazione di un corso"+ e.getMessage());
        }
    }

    @Test
    void deleteById_should_throw_when_course_not_present(){
        CSVFileCourseRepository repo = new CSVFileCourseRepository(FILENAME);
        try{
            repo.deleteById(ID_NOT_PRESENT);
            fail("Non viene lanciata EntityNotFoundExeption quando si cancella un corso già esistente");
        } catch (DataException e) {
            fail("Errore dell'utilizzo del file CSV"+e.getMessage());
        } catch (EntityNotFoundException e) {
            assertEquals(ENTITY_NOT_FOUND + ID_NOT_PRESENT, e.getMessage());
        }
    }

    @Test
    void betterDeleteById_should_throw_when_course_not_present(){
        CSVFileCourseRepository repo = new CSVFileCourseRepository(FILENAME);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                repo.deleteById(ID_NOT_PRESENT);                                           //lambda expression
        });
        assertEquals(ENTITY_NOT_FOUND + ID_NOT_PRESENT, exception.getMessage());
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
    void findByTitleContains_should_find_courses_if_title_present(){
        CSVFileCourseRepository repo = new CSVFileCourseRepository(FILENAME);
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

    private List<String[]> readTokenizedLines() throws IOException{
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        List<String[]> tokenLines = new ArrayList<>();
        for(String s:lines){
            String[] tokens=s.split(",");
            tokenLines.add(tokens);
        }
        return tokenLines;
    }
}