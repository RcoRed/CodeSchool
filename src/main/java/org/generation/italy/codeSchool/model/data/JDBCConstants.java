package org.generation.italy.codeSchool.model.data;

public class JDBCConstants {
    public static final String URL ="jdbc:postgresql://localhost:5432/code_school";
    public static final String USER_NAME="postgresMaster";
    public static final String PASSWORD ="goPostgresGo";
    public static final String COURSE_QUERY="""
                SELECT id_course,title,description,program,duration,is_active,created_at
                FROM courses""";
    public static final String FIND_COURSE_BY_ID ="""
                SELECT id_course,title,description,program, duration,is_active,created_at
                FROM courses WHERE id = ? 
                """;
    public static final String DELETE_COURSE_BY_ID= """
               DELETE FROM courses
               WHERE id = ?
            """;
    public static final String FIND_BY_TITLE_CONTAINS= """
            SELECT id_course,title,description,program, duration,is_active,created_at
            FROM courses WHERE title like ?
            """;
    public static final String INSERT_COURSE= """
            INSERT INTO courses(id_course, title, description, program,duration, is_active, created_at)
              VALUES(?,?, ?, ?, ?, ?, ?);
            """;
    public static final String INSERT_COURSE_RETURNING_ID= """
            INSERT INTO courses(id_course, title, description, program,duration, is_active, created_at)
              VALUES(nextval('course_sequence'),?, ?, ?, ?, ?, ?)
              RETURNING id_course;
            """;
    public static final String NEXT_VAL_COURSE= """
            SELECT nextval('courses_sequence');
            """;
    public static final String UP_DATE_COURSE= """
            UPDATE courses
            SET title = ?,
            SET description = ?,
            SET program = ?,
            SET duration = ?,
            SET is_active = ?,
            SET create_at = ?,
            WHERE id_course = ?;
            """;
    public  static  final String ACTIVE_COURSES= """           
            UPDATE COURSE
            SET IS_ACTIVE = FALSE
            WHERE COURSE_ID IN(
               SELECT COURSE_ID
               FROM COURSE AS C
               WHERE IS_ACTIVE = TRUE
               ORDER BY CREATE_DATE
               LIMIT ?)
            """;
}
