package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;       //!!importante!!

class CSVFileCourseRepositoryTest {

    private static final long ID=1;
    private static final String TITLE="TITLE";
    private static final String DESCRIPTION="DESCRIPTION";
    private static final String PROGRAM="PROGRAM";
    private static final double DURATION=200.0;

    private static final String CSVLINE=String.format("%d,%s,%s,%s,%f",ID,TITLE,DESCRIPTION,PROGRAM,DURATION);

    private static final String FILENAME="TESTDATA.csv";

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        System.out.println("setUp");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }

    @org.junit.jupiter.api.Test
    void findById() {
        System.out.println("findById");
    }

    @org.junit.jupiter.api.Test
    void create() {
        System.out.println("create");
    }

    @org.junit.jupiter.api.Test
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
}