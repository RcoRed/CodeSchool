package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
<<<<<<< HEAD
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
=======
import org.generation.italy.codeSchool.model.data.abstructions.CourseRepository;
>>>>>>> origin/master
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import java.io.*;
<<<<<<< HEAD
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SerializedCourseRepository implements CourseRepository{
    private static long nextId;
    private String fileName;
    public static final String DEFAULT_FILE_NAME = "Corsi.txt";
    public SerializedCourseRepository() {
        this.fileName = DEFAULT_FILE_NAME;
    }
    public SerializedCourseRepository(String fileName) {
        this.fileName = fileName;
    }
    @Override
    public Optional<Course> findById(long id) throws DataException {
        try{
            FileInputStream fileInput= new FileInputStream (fileName);
            ObjectInputStream input = new ObjectInputStream(fileInput);
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String s:lines){
                String[] trimmed = s.split(",");
                long courseId = Long.parseLong(trimmed[0]);
                if (courseId == id){
                    Course found = new Course(courseId,trimmed[1],trimmed[2]
                            ,trimmed[3],Double.parseDouble(trimmed[4]));
                    input.close();
                    fileInput.close();
                    return Optional.of(found);
                }
            }
            return Optional.empty();
        }catch (IOException e){
            throw new DataException("Errore nella lettura del file",e);
=======
import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import static org.generation.italy.codeSchool.model.data.Constants.*;

public class SerializedCourseRepository implements CourseRepository {
    private static final String SERIALIZED_FILE_NAME = "courses.ser";
    public static long nextID;
    private String filename;

    public SerializedCourseRepository(String filename) {
        this.filename = filename;
    }

    public SerializedCourseRepository() {
        this.filename = SERIALIZED_FILE_NAME;
    }

    @Override
    public Optional<Course> findById(long id) throws DataException {
        try {
            var courses = load();
            for(var c : courses) {
                if(c.getId() == id) {
                    return Optional.of(c);
                }
            }
            return Optional.empty();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new DataException("Errore nel create course", e);
>>>>>>> origin/master
        }
    }

    @Override
    public List<Course> findByTitleContains(String part) throws DataException {
<<<<<<< HEAD
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
=======
        var courses = new ArrayList<Course>();
        try {
            var all = load();
            for (Course c : all) {
                if (c.getTitle().contains(part)) {
                    courses.add(c);
                }
            }
            return courses;
        } catch (IOException | ClassNotFoundException e) {
            throw new DataException("Errore nel findByTitleContains", e);
>>>>>>> origin/master
        }
    }

    @Override
    public Course create(Course course) throws DataException {
<<<<<<< HEAD
        try{
            FileOutputStream fileOutput = new FileOutputStream(fileName, true);
            ObjectOutputStream output= new ObjectOutputStream(fileOutput);
            output.writeObject(course);
            output.close();
            fileOutput.close();
            return course;
        }catch (FileNotFoundException e){
            throw new DataException("Errore nel trovare il file", e);
        }catch (IOException e){
            throw new DataException("Errore nel salvataggio su file",e);
        }
    }


    @Override
    public void update(Course course) throws EntityNotFoundException, DataException {

=======
        try {
            var courses = load();
            course.setId(++nextID);
            courses.add(course);
            store(courses);
            return course;
        } catch (IOException | ClassNotFoundException e) {
            throw new DataException("Errore nel create course", e);
        }
    }

    @Override
    public void update(Course course) throws EntityNotFoundException, DataException {
        try {
            var courses = load();
            int pos = -1;
            for(int i=0; i < courses.size(); i++) {
                if(courses.get(i).getId() == course.getId()) {
                    pos = i;
                }
            }
            if(pos == -1) {
                throw new EntityNotFoundException(ENTITY_NOT_FOUND + course.getId());
            }
            courses.set(pos, course);
            store(courses);

        } catch (IOException | ClassNotFoundException e) {
            throw new DataException("Errore nel create course", e);
        }
>>>>>>> origin/master
    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException, DataException {
<<<<<<< HEAD

    }
=======
        try {
            var courses = load();
            for(Iterator<Course> it = courses.iterator(); it.hasNext();) {
                Course c = it.next();
                if(c.getId() == id) {
                    it.remove();
                    //courses.remove(c);
                    store(courses);
                    return;
                }
            }
            throw new EntityNotFoundException(ENTITY_NOT_FOUND + id);

        } catch (IOException | ClassNotFoundException e) {
            throw new DataException("Errore nel create course", e);
        }
    }

    private List<Course> load() throws IOException, ClassNotFoundException {
        File f = new File(filename);
        if (!f.exists()) {
            f.createNewFile();
        }
        if (f.length() == 0) {
            return new ArrayList<>();
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            List<Course> courseList = (List<Course>) ois.readObject();
            return courseList;
        }
    }

    private void store(List<Course> courses) throws IOException{
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(courses);
        }
    }

>>>>>>> origin/master
}
