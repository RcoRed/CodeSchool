package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.entities.CourseEdition;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateCourseEditionRepository extends GenericCRUDRepository<CourseEdition>{
    public HibernateCourseEditionRepository(Session session) {
        super(session, CourseEdition.class);
    }

}
