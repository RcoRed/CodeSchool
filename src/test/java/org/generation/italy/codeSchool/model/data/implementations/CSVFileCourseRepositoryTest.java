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

class CSVFileCourseRepositoryTest {

    private static final long ID=1;
    private static final long ID2=ID+1;
    private static final long ID_NOT_PRESENT=3;
    private static final long ID_CREATE=4;
    private static final String TITLE="TITLE";
    private static final String DESCRIPTION="DESCRIPTION";
    private static final String PROGRAM="PROGRAM";
    private static final double DURATION=200.0;
    private static final String CSVLINE=String.format(Locale.US,"%d,%s,%s,%s,%.2f",ID,TITLE,DESCRIPTION,PROGRAM,DURATION);
    private static final String CSVLINE2=String.format(Locale.US,"%d,%s,%s,%s,%.2f",ID2,TITLE+"Test",DESCRIPTION+"Test",PROGRAM+"Test",DURATION+1);
    private static final String FILENAME="TESTDATA.csv";

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws FileNotFoundException {
        try(PrintWriter pw = new PrintWriter(new FileOutputStream(FILENAME))){
            pw.println(CSVLINE2);
            pw.println(CSVLINE);
            //pw.flush();                                         //obbligo a scrivere subito
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
        Course c1 = new Course(ID, TITLE, DESCRIPTION, PROGRAM, DURATION);
        CSVFileCourseRepository repo = new CSVFileCourseRepository(FILENAME);
        try {
            Optional<Course> x = repo.findById(ID);
            assertTrue(x.isPresent());
            Course c2 = x.get();
            assertEquals(c1, c2);
        } catch (DataException e) {
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
    void deleteById_should_delete_course_if_present() {
        //ARRANGE
        CSVFileCourseRepository  repo = new CSVFileCourseRepository(FILENAME);
        try{
            //ACT
            repo.deleteById(ID);
            //ASSERT
            List<String[]> tokenLines = readTokenizedLines();
            assertEquals(1, tokenLines.size());
            long courseID = Long.parseLong(tokenLines.get(0)[0]);
            assertEquals(ID2,courseID);

        }catch (IOException e){
            fail("Errore nell'utilizzo del file id test .CSV" + e.getMessage());
        }catch (EntityNotFoundException | DataException e){
            fail("Errore nella cancellazione di un corso " + e.getMessage());
        }
    }

    @Test
    void deleteById_should_throw_when_course_not_present() {
        //ARRANGE
        CSVFileCourseRepository  repo = new CSVFileCourseRepository(FILENAME);
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

    @Test
    void courseToCSV() {
        // ARRANGE      //inizializzo i dati che poi dovrò usare
        Course c = new Course(ID,TITLE,DESCRIPTION,PROGRAM,DURATION);
        CSVFileCourseRepository  repo = new CSVFileCourseRepository(FILENAME);
        // ACT          //richiamo ciò che devo testare
        String csvLine = repo.CourseToCSV(c);
        // ASSERT       //prego che tutto sia andato bene
        //Assertions.assertEquals(1,1);     //possiamo fare assertEquals() perchè l'import è STATIC (quindi evitiamo di scrivere "Assertations." prima)
        assertEquals(CSVLINE,csvLine);
    }

    private List<String[]> readTokenizedLines() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        List<String[]> tokenLines = new ArrayList<>();
        for (String s:lines){
            tokenLines.add(s.split(","));
        }
        return tokenLines;
    }
}