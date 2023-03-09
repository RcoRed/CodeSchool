package org.generation.italy.codeSchool.model.data;

public class JDBCConstants {
    public static final String URL = "jdbc:postgresql://localhost:5432/legion";
    public static final String USER_NAME = "postgresMaster";
    public static final String PASSWORD = "goPostgresGo";
    public static final String COURSE_QUERY = """
            SELECT id_course, title, description, program, duration, is_active, created_at
            FROM course""";
    public static final String FIND_COURSE_BY_ID = """
            SELECT id_course, title, description, program, duration,is_active,created_at
            FROM course WHERE id_course = ?
            """;
    public static final String DELETE_COURSE_BY_ID = """
               DELETE FROM course
               WHERE id_course = ?
            """;
    public static final String FIND_BY_TITLE_CONTAINS = """
            SELECT id_course,title,description,program, duration,is_active,created_at
            FROM course WHERE title like ?
            """;
    public static final String INSERT_COURSE = """
            INSERT INTO course(id_course, title, description, program, duration, is_active, created_at)
            VALUES(?, ?, ?, ?, ?, ?, ?);
            """;
    public static final String INSERT_COURSE_RETURNING_ID = """
            INSERT INTO course(id_course, title, description, program, duration, is_active, created_at)
            VALUES(nextval('course_sequence'),?, ?, ?, ?, ?, ?)
            RETURNING id_course;
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
            SET created_at = ?,
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
            WHERE id_course IN (
                SELECT c2.id_course
                FROM course AS c2
                WHERE c2.is_active = true
                ORDER BY c2.created_at asc
                LIMIT ?
            );
            """;


    // COURSE EDITION
    public static final String FIND_MOST_EXPENSIVE_COURSE = """
            SELECT ce.id_course_edition, c.title, ce.started_at, ce.price, ce.id_classroom
                FROM course_edition AS ce JOIN course AS c
                USING (id_course)
                ORDER BY price desc
                LIMIT 1
            """;

    public static final String FIND_BY_COURSE = """
            SELECT ce.id_course_edition, c.title, ce.started_at, ce.price, ce.id_classroom
            FROM course AS c JOIN course_edition AS ce
            USING (id_course)
            WHERE id_course = ?
            """;

    public static final String FIND_BY_COURSE_AND_TITLE = """
            SELECT ce.id_course_edition,ce.id_course,ce.started_at,ce.price,ce.id_classroom,
            c.title,c.description,c.program,c.duration,c.is_active,c.created_at
            FROM course_edition as ce JOIN course as c
            USING (id_course)
            WHERE ce.id_course = ? AND c.title LIKE ? AND ce.started_at BETWEEN ? AND ?
            """;

}
