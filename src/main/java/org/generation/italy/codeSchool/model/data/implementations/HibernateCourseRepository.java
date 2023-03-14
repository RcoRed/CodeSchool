package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class HibernateCourseRepository extends GenericCRUDRepository <Course> implements CourseRepository {
   @Autowired              //Adesso averrà l'iniezione
   private Session session;

   public HibernateCourseRepository(Session session) {
      super(session, HibernateCourseRepository.class);
   }

   //   @Override
//   public List<Course> findAll() throws DataException {
//      return session.createQuery("from Course", Course.class).list();
//   }
//
//   @Override
//   public Optional<Course> findById(long id) throws DataException {
//      Course found = session.get(Course.class, id);
//      return found != null ? Optional.of(found) : Optional.empty();
//   }
//
//   @Override
//   public Course create(Course course) throws DataException {
//      session.persist(course); //non ci serve anche fare update qua, quindi non usiamo merge
//      return course; //id ancora non è settato, dobbiamo ancora fare il commit
//   }
//
//   @Override
//   public void update(Course course) throws EntityNotFoundException, DataException {
//      session.merge(course); //merge è un persist e un update allo stesso tempo
//   }
//
//   @Override
//   public void deleteById(long id) throws EntityNotFoundException, DataException {
//      Course c = session.getReference(Course.class,id); //non fa una query ma crea un oggetto con id data
//      session.remove(c);   //questo metodo manda solo una query: delete; riceve una proxy (c) al database ()proxy, fa finta di essere qualcun altro
//   }
   @Override
   public int countActiveCourses() throws DataException {
      Query<Integer> i = session.createQuery("select count (*) from Course where isActive = true");
      return i.getSingleResult();
   }

   @Override
   public void deactivateOldest(int n) throws DataException {

   }

   @Override
   public boolean adjustActiveCourses(int NumActive) throws DataException {
      return false;
   }
   @Override
   public List<Course> findByTitleContains(String part) throws DataException {
      Query<Course> q = session.createQuery("from Course where title like :p"); //:part, buco dove metto "part
      q.setParameter("p", "%" + part + "%");
      return q.list();
   }
}
