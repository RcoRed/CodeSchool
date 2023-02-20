package org.generation.italy.codeSchool.model.data.implementations;

import netscape.javascript.JSObject;
import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstructions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import javax.xml.datatype.DatatypeConstants;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SerializedCourseRepository implements CourseRepository, Serializable {
    public static final String DEFAULT_FILE_NAME = "Corsi.txt";
    private String fileName;
    private static long nextId;
    public SerializedCourseRepository(String fileName){
        this.fileName = fileName;
    }

    public SerializedCourseRepository(){

    }

    @Override
    public Optional<Course> findById(long id) throws DataException {
        try{
            List<Course> courses = new ArrayList<>();
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream input = new ObjectInputStream(file);
            courses = (ArrayList<Course>) input.readObject();
            for(Course c : courses){
                if(c.getId() == id){
                    System.out.println("trovato");
                    return Optional.of(c);
                }
            }
            return Optional.empty();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new DataException("Errore nell'utilizzo del file",e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Course> findByTitleContains(String part) throws DataException {
        try {
            List<Course> c;
            List<Course> found= new ArrayList<>();
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            c = (ArrayList<Course>) objectInputStream.readObject();
            for (Course course : c){
                if(course.getTitle().contains(part)){
                    found.add(course);
                    return found;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new DataException("Errore nell'utilizzo del file",e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Course create(Course course) throws DataException {
        try{
            ArrayList<Course> c;
            File f = new File(fileName);
            if(!f.exists()){
                c = new ArrayList<>();
                c.add(course);
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(c);
                objectOutputStream.close();
                fileOutputStream.close();
            }else{
                FileInputStream fileInputStream = new FileInputStream(fileName);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                c = (ArrayList<Course>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
                c.add(course);
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(fileOutputStream);
                objectOutputStream2.writeObject(c);
                objectOutputStream2.close();
                fileOutputStream.close();
            }
            return course;
        }catch (FileNotFoundException e){
            throw new DataException("Errore nel trovare il file",e);
        }catch (IOException e){
            throw new DataException("Errore nell'utilizzo del file",e);
        } catch (ClassNotFoundException e) {
            throw new DataException("Classe non trovata", e);
        }
    }

    @Override
    public void update(Course course) throws EntityNotFoundException, DataException {
        try{
            List<Course> c;
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            c = (ArrayList<Course>) objectInputStream.readObject();
            for(Course course1 : c){
                if(course1.getId() == course.getId()){
                    c.set(c.indexOf(course1), course);
                    try(FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);){
                        objectOutputStream.writeObject(c);
                    }
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException, DataException {
        try {
            List<Course> c;
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            c = (ArrayList<Course>) objectInputStream.readObject();
            for(Course corso : c){
                if(corso.getId() == id){
                    fileInputStream.close();
                    objectInputStream.close();
                    c.remove(corso);
                    try(FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);){
                        objectOutputStream.writeObject(c);
                    }
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
