package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.TeacherRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.entities.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import static org.generation.italy.codeSchool.model.data.JDBCTeacherConstants.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Repository
@Profile("jdbc")
public class JDBCTeacherRepository implements TeacherRepository {
   private Connection con;
   public JDBCTeacherRepository(Connection con) { this.con = con;}
   private List<Teacher> teacherList = new ArrayList<>();
   @Override
   public Iterable<Teacher> findWithCompetenceByLevel(Level teacherLevel) throws DataException {
      try(PreparedStatement ps = con.prepareStatement(FIND_TEACHER_BY_LEVEL)) {
         ps.setObject(1, teacherLevel, Types.OTHER);
         try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
               teacherList.add(teacherRawMapper(rs));
            }
            return teacherList;
         }
      } catch (SQLException e) {
         throw new DataException("Errore nella ricerca del docente a partire dal livello",e);
      }
   }
   //         ***   if (params[i] instanceof Enum<?>)   ***
   private Teacher teacherRawMapper(ResultSet rs) throws SQLException {
      boolean isTeacherAlredyInList = false;
      int posTeacherInTeacherList = -1;
      Sex s = null;
      Level l = null;
//      if (rs.getObject("sex") instanceof Enum<?>) { //SERVE DAVVERO?
//         s = (Sex) rs.getObject("sex");
//      }
//      if (rs.getObject("level") instanceof Enum<?>) {
//         l = (Level) rs.getObject("level");
//      }
      s = (Sex) rs.getObject("sex"); //sti cazzi, al massimo sarà null
      l = (Level) rs.getObject("level");
      Long cheBellId = rs.getLong("id_person"); //altrimenti dovrei gestirmi l'eccezione nella lambda e bleah
      isTeacherAlredyInList = teacherList.stream().anyMatch(teachLover -> teachLover.getId() == cheBellId);
      if (isTeacherAlredyInList) {  //se il teacher esiste provvedo ad aggiungere "solo" la competence
         posTeacherInTeacherList = competenceRawMapper ( cheBellId, l, rs);
      } else {
         Teacher teacher = new Teacher(rs.getLong("id_person"),
               rs.getString("firstname"),
               rs.getString("lastname"),
               rs.getDate("dob").toLocalDate(),
               s,
               rs.getString("email"),
               rs.getString("cell_number"),
               null,
               rs.getString("password"),
               rs.getString("password"),
               new HashSet<>(),
               rs.getString("p_IVA"),
               rs.getBoolean("is_employee"),
               rs.getDate("hire_date").toLocalDate(),
               rs.getDate("fire_date").toLocalDate(),
               l);
         teacherList.add(teacher);
         posTeacherInTeacherList = competenceRawMapper ( cheBellId, l, rs);
      }
      return teacherList.get(posTeacherInTeacherList); // da scrivere ma c'è il barbatrucco
   }

   private int competenceRawMapper ( Long cheBellId, Level l, ResultSet rs) throws SQLException {
      //         Teacher temporaneo;
      int p = -1;
      for (int i = 0; i < teacherList.size(); i++) {
         if(teacherList.get(i).getId() == cheBellId){
//               temporaneo = teacherList.get(i);
            p = i;
         }
      }
//         mi salvo la skill
      Skill skillSimpatica = new Skill(rs.getLong("id_skill"), rs.getString("skill_name"),
            rs.getString("skill_description"));
//         mi salvo la competence
      Competence competenceSimpatica = new Competence(rs.getLong("id_competence"),
            skillSimpatica, teacherList.get(p),l);
//         agrego al teacher la competence
      teacherList.get(p).addCompetence(competenceSimpatica);
//         var te = teacherList.stream()
//                             .filter(teacher -> teacher.getId() == cheBellId)
//                             .map(teacher -> teacher.addCompetence(competenceSimpatica));
      return p;
   }

}
