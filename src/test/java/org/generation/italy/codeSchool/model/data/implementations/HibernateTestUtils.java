package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.entities.Course;
import org.hibernate.Session;

public class HibernateTestUtils {
    public static long insertCourse(Course c, Session s){
        s.persist(c);
        s.flush();
        return c.getId();
    }
}
