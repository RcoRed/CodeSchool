package org.generation.italy.codeSchool.model.data.abstractions;

import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.entities.Level;
import org.generation.italy.codeSchool.model.entities.Teacher;

public interface TeacherRepository {
    Iterable<Teacher> findWithCompetenceByLevel(Level teacherLevel) throws DataException;
}
