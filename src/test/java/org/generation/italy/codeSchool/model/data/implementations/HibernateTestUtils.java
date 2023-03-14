package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.entities.Course;
import org.hibernate.Session;

public class HibernateTestUtils {
   public static  long insertCourse(Course c, Session session){
      session.persist(c);
      session.flush();              //fa mandare i comandi senza committare, così posso caricare dei corsi senza salvarli, e avranno i loro id ecc...
//      session.getTransaction().commit(); non committo per poter fare rollBack
      return c.getId();
   }
}
