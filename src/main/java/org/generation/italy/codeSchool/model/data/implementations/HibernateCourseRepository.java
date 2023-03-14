package org.generation.italy.codeSchool.model.data.implementations;

import jakarta.persistence.Query;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.entities.Course;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class HibernateCourseRepository implements CourseRepository {
    @Autowired
    private Session session;
    @Override
    public List<Course> findAll() throws DataException {
        org.hibernate.query.Query<Course> q = session.createQuery("from Course", Course.class); //utilizza query di Hibernate
        return q.list();
    }

    @Override
    public Optional<Course> findById(long id) throws DataException {
        Course found = session.get(Course.class,id);
        return found!=null ? Optional.of(found) : Optional.empty();
    }

    @Override
    public List<Course> findByTitleContains(String part) throws DataException {
        Query q = session.createQuery("from Course where title like :p", Course.class);
        q.setParameter("p",part);
        return q.getResultList();
    }

    @Override
    public Course create(Course course) throws DataException {
        session.persist(course);
        return course;
    }

    @Override
    public void update(Course course) throws EntityNotFoundException, DataException {
        session.merge(course);
    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException, DataException {
        Course c = session.getReference(Course.class,id);
        session.remove(c);
    }

    @Override
    public int countActiveCourses() throws DataException {
        return 0;
    }

    @Override
    public void deactivateOldest(int n) throws DataException {

    }

    @Override
    public boolean adjustActiveCourses(int NumActive) throws DataException {
        return false;
    }
}
