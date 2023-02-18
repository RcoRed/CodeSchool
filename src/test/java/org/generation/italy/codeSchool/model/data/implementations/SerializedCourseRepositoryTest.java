package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Locale;

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
    private static final String SERLINE1=String.format(Locale.US,"%d,%s,%s,%s,%.2f",ID,TITLE,DESCRIPTION,PROGRAM,DURATION);
    private static final String SERLINE2=String.format(Locale.US,"%d,%s,%s,%s,%.2f",ID2,TITLE+TEST,DESCRIPTION+TEST,PROGRAM+TEST,DURATION+1);
    private static final String SERLINE3=String.format(Locale.US,"%d,%s,%s,%s,%.2f",ID3,TITLE+TEST,DESCRIPTION+TEST,PROGRAM+TEST,DURATION+2);
    private static final String FILENAME="TEST_DATA_SER";

    @BeforeEach
    void setUp() {
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
    void findById() {

    }

    @Test
    void create_should_add_course() throws DataException{
        Course c1 = new Course(ID,TITLE,DESCRIPTION,PROGRAM,DURATION);
        Course c2 = new Course(ID_CREATE,TITLE,DESCRIPTION,PROGRAM,DURATION);
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILENAME))){
            out.writeObject(c1);
            out.writeObject(c2);
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILENAME))){
                Course verifica1 = (Course) in.readObject();
                Course verifica2 = (Course) in.readObject();
                System.out.println(verifica1.getId());
                System.out.println(verifica1.getTitle());
                System.out.println(verifica1.getDescription());
                System.out.println(verifica1.getProgram());
                System.out.println(verifica1.getDuration());
                System.out.println(verifica2.getId());
                System.out.println(verifica2.getTitle());
                System.out.println(verifica2.getDescription());
                System.out.println(verifica2.getProgram());
                System.out.println(verifica2.getDuration());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }catch (IOException e) {
            fail("Fallita la verifica del create su file SER" + e.getMessage());
        }
    }
}