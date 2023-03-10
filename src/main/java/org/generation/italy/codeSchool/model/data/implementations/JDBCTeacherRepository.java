package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.TeacherRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.entities.Level;
import org.generation.italy.codeSchool.model.entities.Teacher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.generation.italy.codeSchool.model.data.JDBCTeacherConstants.*;

@Repository
@Profile("jdbc")
public class JDBCTeacherRepository implements TeacherRepository {
    private Connection con;
    public JDBCTeacherRepository(Connection con){
        this.con = con;
    }
    @Override
    public Iterable<Teacher> findWithCompetencesByLevel(Level teacherLevel) throws DataException {
        try(PreparedStatement st = con.prepareStatement(FIND_TEACHER_BY_LEVEL)){
            st.setObject(1, teacherLevel, Types.OTHER,0);
            try(ResultSet rs = st.executeQuery()) {
                List<Teacher> teacherList = new ArrayList<>();
                while (rs.next()){
                    teacherList.add(teacherRawMapper(rs));
                }
                return teacherList;
            }
        }catch (SQLException e){
            throw new DataException("Errore nella ricerca del docente a partire dal livello",e);
        }
    }

    public Teacher teacherRawMapper(ResultSet rs){
        return null;
    }
}
