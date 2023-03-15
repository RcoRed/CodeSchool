package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.entities.Course;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HibernateCourseRepository extends GenericCRUDRepository<Course> implements CourseRepository{

    /*
    @Autowired
    private Session session;


    @Override
    public List<Course> findAll() throws DataException {
        return session.createQuery("from Course ", Course.class).list();
    }

    @Override
    public Optional<Course> findById(long id) throws DataException {
        Course found = session.get(Course.class, id);
        return found != null ? Optional.of(found) : Optional.empty();
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
        Course c = session.getReference(Course.class, id);
        session.remove(c);
    }
    */

    public HibernateCourseRepository(Session session) {
        super(session, Course.class);
    }

    @Override
    public int countActiveCourses() throws DataException {
        return session.createQuery("select count(*) from Course where isActive = true", Integer.class).getSingleResult();
    }

    @Override
    public void deactivateOldest(int n) throws DataException {
        var courseList = session.createQuery("from Course as c where c.isActive = true order by c.createdAt asc limit "+n, Course.class).list();
        courseList.forEach(c -> c.setActive(false));
        courseList.forEach(c -> session.merge(c));

        //session.createQuery("update Course as c1 set c1.isActive = false where c1.id in (from Course as c where c.isActive = true order by c.createdAt asc limit :n)").setParameter("n", n);
        //session.merge()


    }

    @Override
    public boolean adjustActiveCourses(int NumActive) throws DataException {
        return false;
    }

    @Override
    public List<Course> findByTitleContains(String part) throws DataException {
        return session.createQuery("from Course where title like : p", Course.class)
                .setParameter("p", "%" + part + "%").list();
    }
}
