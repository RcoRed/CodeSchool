package org.generation.italy.codeSchool.model.data;

public class JDBCConstantsEdition {
    public static final String FIND_MOST_EXPENSIVE_COURSE_EDITION = """
            SELECT ce.id_course_edition, ce.started_at, ce.price, c.id_course, c.title, c.description,
                            c.program, c.duration, c.is_active, c.created_at, cr.id_classroom, cr.class_name, cr.max_capacity,
                            cr.is_virtual, cr.is_computerized, cr.has_projector, rp.id_remote_platform, rp.name, rp.annual_cost, rp.adoptiondate
            FROM course_edition as ce
            JOIN course as c
            USING (id_course)
            JOIN classroom as cr
            USING (id_classroom)
            JOIN remote_platform as rp
            USING (id_remote_platform)
            ORDER BY ce.price DESC
            LIMIT 1
            """;
    public static final String FIND_COURSE_EDITION_BY_COURSE_ID = """
            SELECT id_course_edition, id_course, started_at, price, id_classroom
            FROM course_edition
            WHERE id_course = ?
            """;

    public static final String FIND_COURSE_EDITION_BY_COURSE_TITLE_AND_PERIOD = """
            SELECT ce.id_course_edition, ce.id_course, ce.started_at, ce.price, ce.id_classroom
            FROM course_edition as ce
            JOIN course as c
            USING (id_course)
            WHERE (c.title LIKE ?) AND (ce.started_at BETWEEN ? AND ?)
            """;
    public static final String FIND_COURSE_EDITION_BY_TEACHER_ID ="""
            SELECT ce.id_course_edition, ce.id_course, ce.started_at, ce.price, ce.id_classroom
            FROM edition_module as em
            JOIN course_module as cm
            USING (id_course_module)
            JOIN course as c
            USING (id_course)
            JOIN course_edition as ce
            USING (id_course_edition)
            WHERE id_teacher = ?
            """;
}
