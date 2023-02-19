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
            throw new DataException("error", e);
        } catch (ClassNotFoundException e) {
            throw new DataException("errorz", e);
        }
    }

    public SerializedCourseRepository(String fileName, ArrayList<Course> dataSource) {
        this.fileName = fileName;
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Course> findById(long id) throws DataException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))){
            dataSource = (ArrayList<Course>) inputStream.readObject();
            for (Course c : dataSource) {
                if (id == c.getId()) {
                    return Optional.of(c);
                }
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new DataException("Error", e);
        } catch (ClassNotFoundException e) {
            throw new DataException("Class not found", e);
        }
    }

    @Override
    public List<Course> findByTitleContains(String part) throws DataException {
        return null;
    }

    @Override
    public Course create(Course course) throws DataException {
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

    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException, DataException {

    }
}
