package org.generation.italy.codeSchool.model.data;

public class JDBCConstants {
    public static final String URL = "jdbc:postgresql://localhost:5432/code_school";
    public static final String USER_NAME = "postgresMaster";
    public static final String PASSWORD = "goPostgresGo";
    public static final String COURSE_QUERY = """
            SELECT id_course,title,description,program,duration,is_active,created_at
            FROM course""";
    public static final String FIND_COURSE_BY_ID = """
            SELECT course_id,title,description,course_program, duration,is_active,create_date
            FROM course WHERE course_id = ? 
            """;
    public static final String DELETE_COURSE_BY_ID = """
               DELETE FROM course
               WHERE id = ?
            """;
    public static final String FIND_BY_TITLE_CONTAINS = """
            SELECT id_course,title,description,program, duration,is_active,created_at
            FROM course WHERE title like ?
            """;
    public static final String INSERT_COURSE = """
            INSERT INTO course(course_id, title, description, course_program,duration, is_active, create_date)
              VALUES(?,?, ?, ?, ?, ?, ?);
            """;
    public static final String INSERT_COURSE_RETURNING_ID = """
            INSERT INTO course(course_id, title, description, course_program,duration, is_active, create_date)
              VALUES(nextval('course_id'),?, ?, ?, ?, ?, ?)
              RETURNING course_id;
            """;
    public static final String NEXT_VAL_COURSE = """
            SELECT nextval('course_sequence');
            """;
    public static final String UP_DATE_COURSE = """
            UPDATE course
            SET title = ?,
            SET description = ?,
            SET program = ?,
            SET duration = ?,
            SET is_active = ?,
            SET create_at = ?,
            WHERE id_course = ?;
            """;
    public static final String ACTIVE_COURSES = """           
            SELECT COUNT(*) as num_actives
            FROM course
            WHERE is_active = true           
            """;
    public static final String DEACTIVATE_OLDEST_N_COURSES = """
            UPDATE course
                        SET is_active = false
                        WHERE course_id IN (
                          SELECT c2.course_id
                          FROM course AS c2
                          WHERE c2.is_active = true
                          ORDER BY c2.create_date asc
                          LIMIT ?
                        );
            """;

}
