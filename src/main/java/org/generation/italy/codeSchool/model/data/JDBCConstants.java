package org.generation.italy.codeSchool.model.data;

public class JDBCConstants {
    public static final String URL = "jdbc:postgresql://localhost:5432/code_school";
    public static final String USER_NAME = "postgresMaster";
    public static final String PASSWORD = "goPostgresGo";
    public static final String COURSE_QUERY = """
                SELECT id, title, duration, is_active, create_at, program, description
                FROM course""";
    public static final String FIND_COURSE_BY_ID = """
                    SELECT id, title, description, program, duration, is_active, create_at
                    FROM course
                    WHERE id = ?""";
    public static final String CREATE_COURSE = """
                    SELECT id, title, description, program, duration, is_active, create_at
                    FROM course""";
    public static final String FIND_COURSES_BY_TITLE_CONTAIN = """
                    SELECT id, title, description, program, duration, is_active, create_at
                    FROM course
                    WHERE title LIKE(%?%)""";
    public static final String DELETE_COURSES_BY_ID = """
                    DELETE FROM course
                    WHERE id = ?""";
    public static final String FIND_ACTIVE_COURSES= """
                    SELECT id, title, description, program, duration, is_active, create_at
                    FROM course
                    WHERE is_active = true""";
    public static final String DELETE_OLD_ACTIVE_COURSES = """
                    DELETE FROM course
                    WHERE is_active = true && LIMIT
                    
                    SELECT id, title, description, program, duration, is_active, create_at
                    FROM course
                    WHERE is_active = true
                    ORDER BY create_at
                    LIMIT 10""";
}
