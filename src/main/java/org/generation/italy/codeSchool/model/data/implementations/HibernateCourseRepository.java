package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.entities.Course;
import org.hibernate.Session;
import org.hibernate.id.uuid.CustomVersionOneStrategy;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.generation.italy.codeSchool.model.data.HibernateConstants.HQL_DEACTIVATE_OLDEST_N_COURSES;
import static org.generation.italy.codeSchool.model.data.HibernateConstants.HQL_OLDEST_N_COURSES;

@Repository
public class HibernateCourseRepository extends GenericCrudRepository<Course> implements CourseRepository {
//    @Autowired
//    private Session session;
//    @Override
//    public List<Course> findAll() throws DataException {
//        Query<Course> q = session.createQuery("from Course", Course.class); //utilizza query di Hibernate
//        return q.list();
//    }
//
//    @Override
//    public Optional<Course> findById(long id) throws DataException {
//        Course found = session.get(Course.class,id);
//        return found!=null ? Optional.of(found) : Optional.empty();
//    }
//
//    @Override
//    public Course create(Course course) throws DataException {
//        session.persist(course);
//        return course;
//    }
//
//    @Override
//    public void update(Course course) throws EntityNotFoundException, DataException {
//        session.merge(course);
//    }
//
//    @Override
//    public void deleteById(long id) throws EntityNotFoundException, DataException {
//        Course c = session.getReference(Course.class,id);
//        session.remove(c);
//    }
//
//
//
//
//
    public HibernateCourseRepository(Session s){
        super(s, Course.class);
    }
    @Override
    public int countActiveCourses() throws DataException {
        Query<Integer> q = session.createQuery("select count (*) from Course where isActive = true ", Integer.class);
        int n = q.getSingleResult();
        return n;
    }

    @Override
    public void deactivateOldest(int n) throws DataException {
//       MutationQuery q = session.createMutationQuery(HQL_DEACTIVATE_OLDEST_N_COURSES);
//       q.setParameter("limit", n);
//       q.executeUpdate();

       Query<Course> q = session.createQuery(HQL_OLDEST_N_COURSES, Course.class).setParameter("limit", n);
       List<Course> lc = q.list();
       lc.forEach(Course::deactivate);

    }

    @Override
    public boolean adjustActiveCourses(int NumActive) throws DataException {
        return false;
    }
    @Override
    public List<Course> findByTitleContains(String part) throws DataException {
        Query<Course> q = session.createQuery("from Course where title like :p", Course.class);
        q.setParameter("p", "%" + part + "%");
        return q.list();
    }
}