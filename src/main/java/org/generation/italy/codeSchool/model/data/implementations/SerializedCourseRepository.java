package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SerializedCourseRepository implements CourseRepository {
    private static long nextId;
    private String fileName;
    public static final String DEFAULT_FILE_NAME = "Corsi.ser";
    public SerializedCourseRepository() {
        this.fileName = DEFAULT_FILE_NAME;
    }
    public SerializedCourseRepository(String fileName) {
        this.fileName = fileName;
    }
    @Override
    public Optional<Course> findById(long id) throws DataException {
        try{
            FileOutputStream fileOutput= new FileOutputStream(fileName);
            ObjectOutputStream output = new ObjectOutputStream(fileOutput);
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String s:lines){
                String[] trimmed = s.split(",");
                long courseId = Long.parseLong(trimmed[0]);
                if (courseId == id){
                    Course found = new Course(courseId,trimmed[1],trimmed[2]
                            ,trimmed[3],Double.parseDouble(trimmed[4]));
                    output.writeObject(found);
                    output.close();
                    fileOutput.close();
                    return Optional.of(found);
                }
            }
            return Optional.empty();
        }catch (IOException e){
            throw new DataException("Errore nella lettura del file",e);
        }
    }

    @Override
    public List<Course> findByTitleContains(String part) throws DataException {
        try {
            FileOutputStream fileOutput= new FileOutputStream(fileName);
            ObjectOutputStream output = new ObjectOutputStream(fileOutput);
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            List<Course> courses = new ArrayList<>();
            for(String s : lines){
                String[] tokens = s.split(",");
                if(tokens[1].contains(part)){
                    Course found = new Course(Long.parseLong(tokens[0]), tokens[1], tokens[2],
                            tokens[3], Double.parseDouble(tokens[4]));
                    courses.add(found);
                }
            }
            output.writeObject(courses);
            output.close();
            fileOutput.close();
            return courses;
        }catch (IOException e){
            throw new DataException("Errore nella lettura del file", e);
        }
    }

    @Override
    public Course create(Course course) throws DataException {
        try (FileOutputStream fileOutput = new FileOutputStream(fileName,true);
             PrintWriter pw = new PrintWriter(fileOutput)){
            course.setId(++nextId);
            pw.println((course));
            return course;
        }catch (IOException e){
            throw new DataException("Errore nel salvataggio su file",e);
        }
    }


    @Override
    public void update(Course course) throws EntityNotFoundException, DataException {

    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException, DataException {

    }
}
