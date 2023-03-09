package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.entities.Course;

import java.sql.*;
import java.util.Optional;

import static org.generation.italy.codeSchool.model.data.JDBCConstants.*;


public class JDBCTestUtils {
    public static long insertCourse(Course course, Connection con) {
        try (
             PreparedStatement st = con.prepareStatement(INSERT_COURSE_RETURNING_ID, Statement.RETURN_GENERATED_KEYS);//factory method pattern
        ) {
            st.setString(1, course.getTitle());
            st.setString(2, course.getDescription());
            st.setString(3, course.getProgram());
            st.setDouble(4, course.getDuration());
            st.setBoolean(5, course.isActive());
            st.setDate(6, Date.valueOf(course.getCreatedAt()));
            st.executeUpdate();
            try (ResultSet keys = st.getGeneratedKeys()) {
                keys.next();
                long key = keys.getLong(1);
                course.setId(key);
                return course.getId();
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<Course> findCourseById(long id, Connection con) {
        try (
                PreparedStatement st = con.prepareStatement(FIND_COURSE_BY_ID);
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(createCourseFrom(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Course createCourseFrom(ResultSet rs) throws SQLException {

            return new Course(rs.getLong("id_course"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("program"),
                    rs.getDouble("duration"),
                    rs.getBoolean("is_active"),
                    rs.getDate("created_at").toLocalDate());

    }

    public static int update(String query,Connection con,boolean inserting,Object... params){
        try (PreparedStatement st = inserting? con.prepareStatement(query,  Statement.RETURN_GENERATED_KEYS)
                :  con.prepareStatement(query)){
            for(int i = 0; i < params.length; i++){
                st.setObject(i+1,params[i]);
            }
            if(inserting){
                st.executeUpdate();
                try (ResultSet keys = st.getGeneratedKeys()) {
                    keys.next();
                    long key = keys.getLong(1);
                    return (int) key;
                }
            }else {
                return st.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            throw new RuntimeException(e);
        }
    }

}
