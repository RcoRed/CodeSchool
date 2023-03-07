package org.generation.italy.codeSchool.model.data;

public class JDBCConstants {
    public static final String URL ="jdbc:postgresql://localhost:5432/code_school";
    public static final String USER_NAME="postgresMaster";
    public static final String PASSWORD ="goPostgresGo";
    public static final String COURSE_QUERY="""
                SELECT id_course,title,description,course_program,duration,is_active,created_at
                FROM courses
            """;
    public static final String FIND_COURSE_BY_ID="""
                SELECT id_course,title,description,course_program,duration,is_active,created_at
                FROM courses
                WHERE id_course = ?
            """;
    public static final String FIND_BY_TITLE_CONTAINS= """
                SELECT id_course,title,description,course_program,duration,is_active,created_at
                FROM courses
                WHERE title LIKE ?
            """;//chiedere!!!!!!!!!!!
    public static final String DELETE_COURSE_BY_ID= """
                DELETE FROM courses
                WHERE id = ?
            """;
    public static final String INSERT_COURSE= """
                INSERT INTO courses(id_course,title,description,course_program, duration,is_active,created_at)
               	    VALUES(?,?,?,?,?,?,?)
            """;
    public static final String NEXT_VAL_COURSE= """
                SELECT nextval('courses_sequence')
            """;
    public static final String UPDATE_COURSE= """
                UPDATE courses
                SET title = ?
                SET description = ?
                SET course_program = ?
                SET duration = ?
                SET is_active = ?
                SET created_at = ?
                WHERE id_course = ?;
            """;
    public static final String GET_ACTIVE_COURSES= """
                SELECT COUNT(DISTINCT id_course)
                FROM courses
                WHERE is_active = true
            """;
    public static final String DELETE_NUM_ACTIVE_COURSE= """
                SELECT id_course,title,description,course_program,duration,is_active,created_at
                FROM courses
                ORDER By created_at desc
            """;
}
