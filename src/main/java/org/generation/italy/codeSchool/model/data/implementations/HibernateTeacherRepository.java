package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.TeacherRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.entities.Level;
import org.generation.italy.codeSchool.model.entities.Teacher;
import org.hibernate.Session;

import static org.generation.italy.codeSchool.model.data.services.HibernateConstants.*;

public class HibernateTeacherRepository extends GenericCrudRepository<Teacher> implements TeacherRepository {
    public HibernateTeacherRepository(Session session) {
        super(session, Teacher.class);
    }

    @Override
    public Iterable<Teacher> findWithCompetenceByLevel(Level teacherLevel) throws DataException {
       return session.createSelectionQuery(HQL_FIND_TEACHER_BY_LEVEL, Teacher.class)
               .setParameter("level", teacherLevel).list();
    }

    @Override
    public Iterable<Teacher> findWithSkillAndLevel(long idSkill, Level competenceLevel) {
        return session.createSelectionQuery(HQL_FIND_TEACHER_BY_SKILL_LEVEL, Teacher.class)
                .setParameter("level", competenceLevel)
                .setParameter("id", idSkill).list();
    }

    public Iterable<Teacher> findTeacherByNCourseEdition(int n){
        return session.createSelectionQuery(HQL_FIND_TEACHER_BY_N_COURSE_EDITION, Teacher.class)
                .setParameter("n", n).list();
    }
}
