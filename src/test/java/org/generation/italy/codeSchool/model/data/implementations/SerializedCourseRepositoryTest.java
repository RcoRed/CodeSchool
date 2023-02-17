package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class SerializedCourseRepositoryTest{


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findById() {
        Course c = new Course(1,"Come spararsi nel corso di java",
                "con la pistola", "sparasi", 123);
        System.out.println("pippo");
        try{
            System.out.println("pippo3");
            FileOutputStream fileOutput = new FileOutputStream("serializzaione.txt");
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
    }
}