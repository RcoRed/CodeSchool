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
            FROM course WHERE title LIKE ?
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

   public static final String INSERT_COURSE_EDITION_RETURNING_ID = """
            INSERT INTO course_edition(id_course_edition, id_course, started_at, price, id_classroom)
            VALUES (nextval('course_edition_sequence'), ?, ? ,?, ?)
            RETURNING id_course_edition;
            """;

   public static final String INSERT_CLASSROOM_RETURNING_ID = """
            INSERT INTO classroom(id_classroom, class_name, max_capacity, is_virtual, is_computerized, has_projector, 
            id_remote_platform)
            VALUES (nextval('classroom_sequence'), ?, ?, ?, ?, ?, ?)
            RETURNING id_classroom;
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
   public static final String FIND_MOST_EXPENSIVE_COURSE_EDITION = """
         SELECT id_course_edition , started_at, price,
                
                id_course, title, description, program, duration, is_active, created_at,
                
                id_classroom, class_name, max_capacity, is_virtual,
                is_computerized, has_projector, id_remote_platform
         FROM course_edition JOIN course
         USING (id_course)
         JOIN classroom
         USING (id_classroom)
         WHERE price = ( SELECT MAX (price)
         				      FROM   course_edition)
         """;
   public static final String FIND_COURSE_EDITION_BY_COURSE = """
         SELECT id_course_edition , started_at, price,
                id_course, title, description, program, duration, is_active, created_at,
                id_classroom, class_name, max_capacity, is_virtual,
                is_computerized, has_projector, id_remote_platform
         FROM course_edition JOIN course
         USING (id_course)
         JOIN classroom
         USING (id_classroom)
         WHERE id_course = ?
         """;
   public static final String FIND_COURSE_EDITION_BY_COURSE_TITLE_PERIOD = """
         SELECT id_course_edition , started_at, price,
                id_course, title, description, program, duration, is_active, created_at,
                id_classroom, class_name, max_capacity, is_virtual,
                is_computerized, has_projector, id_remote_platform
         FROM course_edition JOIN course
         USING (id_course)
         JOIN classroom
         USING (id_classroom)
         WHERE title LIKE ? AND started_at BETWEEN ? AND ?
         """;
   public static final String FIND_COURSE_EDITION_BY_TEACHER_ID = """
         SELECT id_course_edition , started_at, price,
                id_course, title, description, program, duration, is_active, created_at,
                id_classroom, class_name, max_capacity, is_virtual,
                is_computerized, has_projector, id_remote_platform
         FROM course_edition JOIN course
         USING (id_course)
         JOIN classroom
         USING (id_classroom)
         JOIN edition_module
         USING (id_course_edition)
         WHERE id_teacher = ?
         """;
}
