package org.generation.italy.codeSchool.model.data;

public class JDBCConstants {
    public static final String URL = "jdbc:postgresql://localhost:5432/code_school";
    public static final String USER = "postgresMaster";
    public static final String PASSWORD = "T0t1no2019!";
    public static final String COURSE_QUERY = """
                                    SELECT course_id, course_title, course_description, course_program, course_duration, course_is_active ,course_created_at
                                    FROM course""";

    public static final String FIND_COURSE_BY_ID = """
                                    SELECT course_id, course_title, course_description, course_program, course_duration, course_is_active ,course_created_at
                                    FROM course
                                    WHERE course_id = ?
                                    """;

    public static final String FIND_COURSE_BY_TITLE = """
                                    SELECT course_id, course_title, course_description, course_program, course_duration, course_is_active ,course_created_at
                                    FROM course
                                    WHERE course_title like ?
                                    """;

    public static final String DELETE_COURSE_BY_ID = """
                                    DELETE FROM course
                                    WHERE course_id = ?
                                    """;

    public static final String UPDATE_COURSE = """
            UPDATE course
            SET course_title = ?,
                course_description = ?,
                course_program = ?,
                course_duration = ?,
                course_is_active = ?,
                course_created_at = ?
            WHERE course_id = ?
            """;

    public static final String CREATE_COURSE = """
            INSERT INTO course (course_id, course_title, course_description, course_program, course_duration, course_is_active ,course_created_at)
            VALUES(nextval('course_sequence_id'), ?, ?, ?, ?, ?, ?)
            """;

    public static final String CREATE_COURSE_RETURNING_ID = """
            INSERT INTO course (course_id, course_title, course_description, course_program, course_duration, course_is_active ,course_created_at)
            VALUES(nextval('course_sequence_id'), ?, ?, ?, ?, ?, ?)
            RETURNING course_id
            """;

    public static final String ACTIVE_COURSES = """
            SELECT COUNT(*) AS num_actives
            FROM course
            WHERE course_is_active = true
            """;
}

