package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.entities.CourseEdition;
import org.hibernate.Session;

public class HibernateCourseEditionRepository extends GenericCRUDRepository<CourseEdition> {
   public HibernateCourseEditionRepository(Session session) {
      super(session, CourseEdition.class);
   }
}
