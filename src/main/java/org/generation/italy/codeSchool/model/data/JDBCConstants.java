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

    public static final String FIND_MOST_EXPENSIVE_COURSE_EDITION = """
            SELECT id_course_edition, id_course, started_at, price, id_classroom,
            title, description, program, duration, is_active, created_at,
            class_name, max_capacity, is_virtual, is_computerized, has_projector, id_remote_platform
            FROM course_edition JOIN course
            USING (id_course)
            JOIN classroom
            USING (id_classroom)
            WHERE price = (SELECT MAX(price) FROM course_edition)
            """;

    public static final String FIND_COURSE_EDITION_BY_COURSE = """
            SELECT id_course_edition, id_course, started_at, price, id_classroom,
            title, description, program, duration, is_active, created_at,
            class_name, max_capacity, is_virtual, is_computerized, has_projector, id_remote_platform
            FROM course_edition JOIN course
            USING (id_course)
            JOIN classroom
            USING (id_classroom)
            WHERE id_course = ?
            """;

    public static final String FIND_COURSE_EDITION_BY_COURSE_TILE_AND_PERIOD = """
            SELECT id_course_edition, id_course, started_at, price, id_classroom,
            title, description, program, duration, is_active, created_at,
            class_name, max_capacity, is_virtual, is_computerized, has_projector, id_remote_platform
            FROM course_edition JOIN course
            USING (id_course)
            JOIN classroom
            USING (id_classroom)
            WHERE title LIKE ? AND started_at BETWEEN ? AND ?
            """;

    public static final String FIND_COURSE_EDITION_BY_TEACHER_ID = """
            SELECT id_course_edition, id_course, started_at, price, id_classroom,
            title, description, program, duration, is_active, created_at,
            class_name, max_capacity, is_virtual, is_computerized, has_projector, id_remote_platform
            FROM course_edition JOIN course
            USING (id_course)
            JOIN classroom
            USING (id_classroom)
            JOIN edition_module
            USING (id_course_edition)
            WHERE id_teacher = ?
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
    public static final String INSERT_CATEGORY_RETURNING_ID = """
            INSERT INTO category(id_category, name)
            VALUES (nextval('category_sequence'), ?)
            RETURNING id_category;
            """;
    public static final String INSERT_SKILL_RETURNING_ID = """
            INSERT INTO skill(id_skill, name, id_category)
            VALUES (next('skill_sequence'), ?, ?)
            RETURNING id_skill
            """;
    public static final String INSERT_COMPETENCE_RETURNING_ID = """
            INSERT INTO competence(id_competence, id_person, id_skill, level)
            VALUES(nextval('person_sequence'), ?, ?, ?)
            RETURNING id_competence;
            """;
    public static final String INSERT_PERSON_RETURNING_ID = """
            INSERT INTO person(id_person, firstname, lastname, dob, sex, email, username, password)
            VALUES(nextval('person_sequence'), ?, ?, ?, ?, ?, ?, ?)
            RETURNING id_person;
            """;
    public static final String INSERT_TEACHER = """
            INSERT INTO teacher(id_teacher, p_IVA, is_employee, level)
            VALUES(?, ?, ?, ?)
            """;
    public static final String INSERT_EDITION_MODULE_RETURNING_ID = """
            INSERT INTO edition_module(id_edition_course_module, id_course_edition,id_teahcer)
            VALUES(nextval('edition_module_sequence'), ?, ?)
            RETURNING id_edition_module;
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
}
