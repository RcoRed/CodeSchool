package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.entities.Level;
import org.generation.italy.codeSchool.model.entities.Teacher;

public interface TeacherRepository extends AbstractCrudRepository<Teacher>{
    Iterable<Teacher> findWithCompetenceByLevel(Level teacherLevel) throws DataException;
    Iterable<Teacher> findWithSkillAndLevel(long idSkill, Level competenceLevel) ;
    Iterable<Teacher> findByNEditionModule(int n);
}
