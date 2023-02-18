package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import static org.generation.italy.codeSchool.model.data.Constants.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SerializedCourseRepository implements CourseRepository, Serializable {
   private String fileName;
   private static long nextId;

   public static final String DEFAULT_FILE_NAME = "Corsi.ser";
   private static Map<Long,Course> dataSource = new HashMap<>();

   public SerializedCourseRepository() {
      this.fileName = DEFAULT_FILE_NAME;
   }
   public SerializedCourseRepository(String fileName) {
      this.fileName = fileName;
   }
   @Override
   public Optional<Course> findById(long id) throws DataException {
      try(ObjectInputStream fIS = new ObjectInputStream(new FileInputStream(fileName))) {
         dataSource.putAll((Map<Long, Course>) fIS.readObject());
         Course x = dataSource.get(id);
         if (x != null) { //si hay curso, no es un curso vacìo
            return Optional.of(x);
         }
      } catch (IOException e) {
         new DataException("Errore nella lettura del file",e);
      } catch (ClassNotFoundException e) {
         new EntityNotFoundException(ENTITY_NOT_FOUND);
      }
      return Optional.empty();
   }

   @Override
   public List<Course> findByTitleContains(String part) throws DataException {
      List<Course> coursesFinded = new ArrayList<>();
      try(ObjectInputStream fIS = new ObjectInputStream(new FileInputStream(fileName))){
         dataSource = new HashMap<>();
         dataSource.putAll((Map<Long, Course>) fIS.readObject());
         Collection<Course> gumCollectionCourse = dataSource.values();
         for(Course mySweetCourse : gumCollectionCourse){
            if(mySweetCourse.getTitle().contains(part)){
               coursesFinded.add(mySweetCourse);
            }
         }
         return coursesFinded;
      } catch (FileNotFoundException | ClassNotFoundException e) {
         new EntityNotFoundException(ENTITY_NOT_FOUND);
      } catch (IOException e) {
         throw new DataException("Errore nell'utilizzo del dato", e.getCause());
      }
      return null;
   }

   @Override
   public Course create(Course course) throws DataException { //QUANDO CREO AGGIUNGO A PROSSIMO ID, NON SOSTITUISCO!!!
      try (ObjectInputStream fIS = new ObjectInputStream(new FileInputStream(fileName))) {
         dataSource = (Map<Long, Course>) fIS.readObject();
         course.setId(++nextId);
         dataSource.put(course.getId(), course);
         try(ObjectOutputStream fOS = new ObjectOutputStream(new FileOutputStream(fileName))){
            fOS.writeObject(dataSource);
         }
         return course;
      } catch (FileNotFoundException |ClassNotFoundException e) {
         new EntityNotFoundException(ENTITY_NOT_FOUND);
      } catch (IOException e) {
         throw new DataException(e.getMessage(), e);
      }
      return null;
   }

   @Override
   public void update(Course course) throws EntityNotFoundException, DataException {
      try (ObjectInputStream fIS = new ObjectInputStream(new FileInputStream(fileName))) {
         dataSource = (Map<Long, Course>) fIS.readObject();
         if(dataSource.containsKey(course.getId())){
            dataSource.put(course.getId(), course);
         } else {
//            System.out.println("Non esiste un corso con id: " + course.getId());
            throw new EntityNotFoundException("Non esiste un corso con id: " + course.getId());
         }
         try (ObjectOutputStream fOS = new ObjectOutputStream(new FileOutputStream(fileName))){
            fOS.writeObject(dataSource);
         }
      }catch (FileNotFoundException | ClassNotFoundException naruto){
         throw new EntityNotFoundException(ENTITY_NOT_FOUND);
      }catch(IOException naruto){
         throw new DataException("Errore nell'utilizzo del dato", naruto);
      }
   }

   @Override
   public void deleteById(long id) throws EntityNotFoundException, DataException {
      try(ObjectInputStream fIS = new ObjectInputStream(new FileInputStream(fileName))){
         dataSource = (Map<Long, Course>) fIS.readObject();
         if(dataSource.remove(id) == null){
            throw new EntityNotFoundException("Non esiste un corso con id: " + id);
         }
         try(ObjectOutputStream fOS = new ObjectOutputStream(new FileOutputStream(fileName))){
            fOS.writeObject(dataSource);
         }
      } catch (FileNotFoundException | ClassNotFoundException e) {
         throw new EntityNotFoundException(ENTITY_NOT_FOUND);
      } catch (IOException e) {
         throw new DataException("Errore col flusso di dati", e);
      }
   }
}
