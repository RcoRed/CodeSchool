package org.generation.italy.codeSchool.model.data.implementations;


import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.entities.Course;
import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class HibernateCourseRepository extends GenericCrudRepository<Course> implements CourseRepository{
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
//        Course c = session.getReference(Course.class,id); // crea un oggetto di tipo Course e "fa finta" di averlo letto dal db
//        session.remove(c);
//    }

    public HibernateCourseRepository(Session session){
        super(session, Course.class);
    }

    @Override
    public int countActiveCourses() throws DataException {
        Query<Integer> q = session.createQuery("select count(*) from Course isActive = true", Integer.class);
        return q.getSingleResult();
    }

    @Override
    public List<Course> findByTitleContains(String part) throws DataException {
        Query<Course> q = session.createQuery("from Course where title like :p", Course.class);
        q.setParameter("p", "%" + part + "%");
        return q.list();
    }

    @Override
    public void deactivateOldest(int n) throws DataException {
        MutationQuery q = session.createMutationQuery("update Course set isActive = false where id in (select co from Course co where co.isActive = true order by co.createdAt asc limit :n)");
//        Query<Course> q = session.createQuery("from Course as c where c.isActive = true order by c.createdAt asc limit :n", Course.class);
        q.setParameter("n", n);
        q.executeUpdate();
    }

//                UPDATE course
//                SET is_active = false
//                WHERE id_course IN (
//                    SELECT c2.id_course
//                    FROM course AS c2
//                    WHERE c2.is_active = true
//                    ORDER BY c2.created_at asc
//                    LIMIT ?
//                );
    @Override
    public boolean adjustActiveCourses(int NumActive) throws DataException {
        return false;
    }
}
