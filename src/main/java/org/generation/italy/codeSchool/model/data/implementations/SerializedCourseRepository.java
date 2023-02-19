package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SerializedCourseRepository implements CourseRepository {
    private String fileName;
    private ArrayList<Course> dataSource;
    public SerializedCourseRepository(String fileName) throws FileNotFoundException, DataException {
        this.fileName = fileName;
        try (ObjectInputStream writeFile = new ObjectInputStream(new FileInputStream(fileName))){
            this.dataSource = (ArrayList<Course>) writeFile.readObject();
        } catch (IOException e) {
            throw new DataException("Errore nell'interazione col file ", e);
        } catch (ClassNotFoundException e) {
            throw new DataException("Classe non trovata", e);
        }
    }

    @Override
    public Optional<Course> findById(long id) throws DataException {
        for (Course c : dataSource) {
            if (id == c.getId()) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Course> findByTitleContains(String part) {
        List<Course> result = null;
        for (Course c : dataSource) {
            if (c.getTitle().contains(part)) {
                result.add(c);
            }
        }
        return result;
    }

    @Override
    public Course create(Course course) {
        dataSource.add(course);
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName))) {
            os.writeObject(dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return course;
    }

    @Override
    public void update(Course course) throws EntityNotFoundException, DataException {
        if (findById(course.getId()).equals(Optional.of(course))) {
            deleteById(course.getId());
            create(course);
        } else {
            throw new EntityNotFoundException("Course not found");
        }
    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException, DataException {
        Optional<Course> course = Optional.empty();
        for (Course c : dataSource) {
            if (id == c.getId()) {
                course = Optional.of(c);
            }
        }
        if (findById(id).equals(course)) {
            dataSource.remove(course);
        } else {
            throw new EntityNotFoundException("Course with id: "+id+" not found");
        }
    }
}
