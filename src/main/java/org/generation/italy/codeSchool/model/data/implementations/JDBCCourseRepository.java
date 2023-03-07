package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.codeSchool.model.entities.Course;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.codeSchool.model.data.JDBCConstants.*;

public class JDBCCourseRepository implements CourseRepository {
    @Override
    public Optional<Course> findById(long id) throws DataException {
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement st = con.prepareStatement(FIND_BY_ID_Q)){
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Course c = new Course(rs.getLong("course_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("course_program"),
                            rs.getDouble("duration"),
                            rs.getBoolean("is_active"), // tipo diverso
                            rs.getDate("create_date").toLocalDate());
                    return Optional.of(c);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException("Error in retrieving information from database", e);
        }
    }

    @Override
    public List<Course> findByTitleContains(String part) throws DataException {
        return null;
    }

    @Override
    public Course create(Course course) throws DataException {
        return null;
    }

    @Override
    public void update(Course course) throws EntityNotFoundException, DataException {

    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException, DataException {

    }

    @Override
    public int getActiveCourses() {
        return 0;
    }

    @Override
    public boolean adjustActiveCourses(int NumActive) throws DataException {
        return false;
    }
}
