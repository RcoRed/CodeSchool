package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import java.io.*;
import java.util.*;

public class SerializedCourseRepository implements CourseRepository {
    private String fileName;
    private static long nextId;
    public static final String DEFAULT_FILE_NAME = "Corsi.txt";
    //private static Map<Long,Course> dataSource = new HashMap<>();     //non si può serializzare non implementa serializable
    //hashMap implementa serializable ma non ho ancora cercato come si usa
    //ArrayList implementa serializable dovrebbe andare bene
    //private static ArrayList<Course> dataSource = new ArrayList<>();
    public SerializedCourseRepository() {
        this.fileName = DEFAULT_FILE_NAME;
    }
    public SerializedCourseRepository(String fileName) {
        this.fileName = fileName;
    }

    //non mi piace nessuno dei due metodi
    @Override
    public Optional<Course> findById(long id) throws DataException, EntityNotFoundException {
        ArrayList<Course> dataSource;
        try {
            dataSource = getDeserializedCourses();
            for (Course c:dataSource){
                if (c.getId() == id){
                    return Optional.of(c);
                }
            }
            return Optional.empty();
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Errore nella ricerca di ID: " + e.getMessage());
        } catch (IOException e) {
            throw new DataException("Errore nella lettura del file",e);
        }
    }
    @Override
    public List<Course> findByTitleContains(String part) throws EntityNotFoundException, DataException {
        ArrayList<Course> dataSource;
        try {
            dataSource = getDeserializedCourses();
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new DataException("Errore nella lettura del file",e);
        }
        List<Course> titleCourse = new ArrayList<>();
        for (Course c : dataSource){
            if (c.getTitle().contains(part)) {
                titleCourse.add(c);
            }
        }
        return titleCourse;
    }

    @Override
    public Course create(Course course) throws EntityNotFoundException, DataException {
        ArrayList<Course> dataSource;
        try {
            dataSource = getDeserializedCourses();
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new DataException("Errore nella lettura del file",e);
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))){
            course.setId(++nextId);
            dataSource.add(course);
            out.writeObject(dataSource);
        }catch (IOException e) {
            throw new DataException("Errore nel salvataggio sul file",e);
        }
        return course;
    }

    @Override
    public void update(Course course) throws EntityNotFoundException, DataException {
        ArrayList<Course> dataSource;
        try {
            dataSource = getDeserializedCourses();
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new DataException("Errore nella lettura del file",e);
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))){
            for (Course c : dataSource){
                if (course.getId() == c.getId()) {
                    dataSource.remove(c);
                    dataSource.add(course);
                    out.writeObject(dataSource);
                    break;
                }
            }
        }catch (IOException e) {
            throw new DataException("Errore nel salvataggio sul file",e);
        }
    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException, DataException {
        ArrayList<Course> dataSource;
        try {
            dataSource = getDeserializedCourses();
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new DataException("Errore nella lettura del file",e);
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))){
            for (Course c : dataSource){
                if (id == c.getId()) {
                    dataSource.remove(c);
                    out.writeObject(dataSource);
                    return;
                }
            }
            throw new EntityNotFoundException("Non esiste un corso con ID: " + id);
        }catch (IOException e) {
            throw new DataException("Errore nel salvataggio sul file",e);
        }
    }
    private ArrayList<Course> getDeserializedCourses() throws EntityNotFoundException, IOException {
        ArrayList<Course> dataSource;
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))){

            dataSource = (ArrayList<Course>) in.readObject();

            return dataSource;
        } catch (ClassNotFoundException e) {
            throw new EntityNotFoundException("Classe non trovata " + e.getMessage());
        } catch (IOException e){
            throw new IOException(e);
        }
    }
}
