package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import java.io.*;
import java.util.*;

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
    public List<Course> findAll() throws DataException {
        try {
            return load();
        } catch (IOException | ClassNotFoundException e) {
            throw new DataException("Errore nel findByTitleContains", e);
        }
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
        }
    }

    @Override
    public List<Course> findByTitleContains(String part) throws DataException {
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
        }
    }

    @Override
    public Course create(Course course) throws DataException {
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
    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException, DataException {
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

    @Override
    public int countActiveCourses() throws DataException {
        try {
          return (int) load().stream()
                    .filter(Course::isActive)
                    .count();
        } catch (IOException | ClassNotFoundException e) {
            throw new DataException("Errore nel conteggio dei corsi attivi", e);
        }
    }

    @Override
    public void deactivateOldest(int n) throws DataException {
       try {
           load().stream()
                   .filter(Course::isActive)
                   .sorted(Comparator.comparing(Course::getCreatedAt))
                   .limit(n)
                   .forEach(Course::deactivate);
       }catch (IOException | ClassNotFoundException e) {
           throw new DataException("Errore nella disattivazione dei corsi più vecchi", e);
       }


    }

    @Override
    public boolean adjustActiveCourses(int NumActive) throws DataException {
        return false;
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

}