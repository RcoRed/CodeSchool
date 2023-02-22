package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;       //!!importante!!
import static org.generation.italy.codeSchool.model.data.Constants.*;
import static org.generation.italy.codeSchool.model.data.implementations.TestConstants.*;

class CSVFileCourseRepositoryTest {


    private static final String CSVLINE1=String.format(Locale.US,CSV_COURSE, ID1,TITLE,DESCRIPTION,PROGRAM,DURATION,IS_ACTIVE,CREATED_AT.toString());
    private static final String CSVLINE2=String.format(Locale.US,CSV_COURSE,ID2,TITLE+TEST,DESCRIPTION+TEST,
            PROGRAM+TEST,DURATION+1,IS_ACTIVE, CREATED_AT.toString());
    private static final String CSVLINE3=String.format(Locale.US,CSV_COURSE,ID3,TITLE+TEST,DESCRIPTION+TEST,
            PROGRAM+TEST,DURATION+2,IS_ACTIVE,CREATED_AT.toString());
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
        Course c1 = new Course(ID1,TITLE,DESCRIPTION,PROGRAM,DURATION, LocalDate.now());
        CSVFileCourseRepository  repo = new CSVFileCourseRepository(FILENAME);
        try{                                                                 //obbligo a scrivere subito
            Optional<Course> x = repo.findById(ID1);
            assertTrue(x.isPresent());
            Course c2 = x.get();
            assertEquals(c1,c2);
        }catch (DataException e){
            fail("Errore nella ricerca by id sul file di testo" + e.getMessage());
        }
    }

    @Test
    void create() {
        // ARRANGE
        Course c = new Course(ID_CREATE,TITLE,DESCRIPTION,PROGRAM,DURATION, LocalDate.now());
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
            assertEquals(CSVFileCourseRepository.nextId,Long.parseLong(tokens[0]));
            assertEquals(DURATION,Double.parseDouble(tokens[4]));
        }catch (DataException e){
            fail("Fallito il create su file CSV" + e.getMessage());
        }catch (IOException e){
            fail("Fallita la verifica del create su file CSV" + e.getMessage());
        }
    }
    @Test
    void create_should_save_even_when_file_dont_exist(){
        try{
            Course c = new Course(ID_CREATE,TITLE,DESCRIPTION,PROGRAM,DURATION,LocalDate.now());
            CSVFileCourseRepository  repo = new CSVFileCourseRepository(FILENAME);
            File f = new File(FILENAME);
            assertTrue(f.delete());
            repo.create(c);
            List<String> linesAfter = Files.readAllLines(Paths.get(FILENAME));
            // ASSERT
            assertEquals(1,linesAfter.size());
            String csvLine = linesAfter.get(0);
            String[] tokens = csvLine.split(",");
            assertEquals(CSVFileCourseRepository.nextId,Long.parseLong(tokens[0]));
            assertEquals(DURATION,Double.parseDouble(tokens[4]));
        }catch (DataException e){
            fail("Fallito il create su file CSV" + e.getMessage());
        }catch (IOException e){
            fail("Fallita la verifica del create su file CSV" + e.getMessage());
        }

    }
    @Test
    void findById_should_not_throw_when_file_dont_exist(){
        Course c1 = new Course(ID1,TITLE,DESCRIPTION,PROGRAM,DURATION, LocalDate.now());
        CSVFileCourseRepository  repo = new CSVFileCourseRepository(FILENAME);
        try{                                                                 //obbligo a scrivere subito
            File f = new File(FILENAME);
            assertTrue(f.delete());
            Optional<Course> x = repo.findById(ID1);
            assertFalse(x.isPresent());
        }catch (DataException e){
            fail("Errore nella ricerca by id sul file di testo" + e.getMessage());
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
            repo.deleteById(ID1);
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
        Course c = new Course(ID1,TITLE,DESCRIPTION,PROGRAM,DURATION, LocalDate.now());
        CSVFileCourseRepository  repo = new CSVFileCourseRepository(FILENAME);
        // ACT          //richiamo ciò che devo testare
        String csvLine = repo.courseToCSV(c);
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


    @Test
    void update_should_change_course_if_present(){
        CSVFileCourseRepository repo = new CSVFileCourseRepository(FILENAME);
        Course c = new Course(ID1,TITLE_UPDATED,DESCRIPTION_UPDATED,PROGRAM,DURATION, LocalDate.now());
        try {
            repo.update(c);
            var courses = readAll();
            boolean found = false;
            for(var course : courses) {
                if(course.getId() == c.getId()) {
                    assertEquals(TITLE_UPDATED, course.getTitle());
                    assertEquals(DESCRIPTION_UPDATED, course.getDescription());
                    found = true;
                    break;
                }
            }
            assertTrue(found);

        } catch (EntityNotFoundException e) {
            fail("corso da modificare non trovato quando dovrebbe essere presente " + e.getMessage());
        } catch (IOException | DataException e) {
            fail("Errore nell' update di corsi " + e.getMessage());
        }
    }


    @Test
    void update_should_throw_if_course_absent(){
        CSVFileCourseRepository repo = new CSVFileCourseRepository(FILENAME);
        Course c = new Course(ID_NOT_PRESENT,TITLE_UPDATED,DESCRIPTION_UPDATED,PROGRAM,DURATION, LocalDate.now());

        Exception e = assertThrows(EntityNotFoundException.class, () -> {
            repo.update(c);
        }) ;
        assertEquals(ENTITY_NOT_FOUND + ID_NOT_PRESENT, e.getMessage());
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

    private List<Course> readAll() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        List<Course> courses = new ArrayList<>();
        for(var s : lines) {
            String[] tokens = s.split(",");
            Course c = new Course(Long.parseLong(tokens[0]), tokens[1], tokens[2],
                    tokens[3], Double.parseDouble(tokens[4]),Boolean.valueOf(tokens[5]),LocalDate.parse(tokens[6]));
            courses.add(c);
        }
        return courses;
    }
}