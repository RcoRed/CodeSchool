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
    public static final String DEFAULT_FILE_NAME = "Corsi";
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
        ArrayList<Course> dataSource = null;
        try {
            dataSource = getDeserializedCourses();
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new DataException("Errore nella lettura del file",e);
        }
        for (Course c:dataSource){
            if (c.getId() == id){
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }
//    @Override
//    public Optional<Course> findById(long id) throws DataException, EntityNotFoundException {
//        ArrayList<Course> courseList = new ArrayList<>();
//        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))){
//
//            while (true){
//                courseList.add((Course)in.readObject());
//            }
//
//        } catch (ClassNotFoundException e) {
//            throw new EntityNotFoundException("Classe non trovata " + e.getMessage());
//        }catch (IOException e){
//            if (courseList.isEmpty()){
//                throw new DataException("Errore nella lettura del file " , e);
//            }
//        }
//        for (Course c:courseList){
//            if (c.getId() == id){
//                return Optional.of(c);
//            }
//        }
//        return Optional.empty();
//    }
    //non mi piace nessuno dei due metodi
//    public Optional<Course> findByIdOtherOption(long id) throws DataException, EntityNotFoundException {
//        ArrayList<Course> courseList = new ArrayList<>();
//        int lunghezzaFile = 0;
//        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))){
//
//            lunghezzaFile = in.read();
//            for (int i=0;i<lunghezzaFile;i++){
//                Course c = (Course)in.readObject();
//                if (c.getId() == id){
//                    return Optional.of(c);
//                }
//            }
//
//        }catch (IOException e){
//            if (courseList.isEmpty()){
//                throw new DataException("Errore nella lettura del file " , e);
//            }
//        } catch (ClassNotFoundException e) {
//            throw new EntityNotFoundException("Classe non trovata " + e.getMessage());
//        }
//        return Optional.empty();
//    }

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
                    break;
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

//    private void getDeserializedCourses() throws IOException, ClassNotFoundException {
//        ArrayList<Course> list = new ArrayList<>();
//        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))){
//
//            while (true){
//                list.add((Course)in.readObject());
//            }
//
//        }catch (ClassNotFoundException e){
//            throw new ClassNotFoundException(e.getMessage());
//            //return list;
//        }
//
//    }
}
