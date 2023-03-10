package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.TeacherRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.entities.Level;
import org.generation.italy.codeSchool.model.entities.Teacher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import static org.generation.italy.codeSchool.model.data.JDBCTeacherConstants.*;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("jdbc")
public class JDBCTeacherRepository implements TeacherRepository {
   private Connection con;
   public JDBCTeacherRepository(Connection con){
      this.con = con;
   };
   @Override
   public Iterable<Teacher> findWithCompetenceByLevel(Level teacherLevel) throws DataException {
      try(PreparedStatement ps = con.prepareStatement(FIND_TEACHER_BY_LEVEL)){
         ps.setObject(1, teacherLevel, Types.OTHER);
         try(ResultSet rs = ps.executeQuery()){
            List<Teacher> teacherList = new ArrayList<>();
            while(rs.next()){
               teacherList.add(teacherRawMapper(rs));
            }
         }
      } catch (SQLException e) {
         throw new DataException("Errore nella ricerca di docente dal livello", e);
      }
      return null;
   }

   private Teacher teacherRawMapper(ResultSet rs){
      return new Teacher()
   }

}
