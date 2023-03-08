package org.generation.italy.codeSchool.model.data;

public class JDBCConstantsEdition {
    public static final String FIND_MOST_EXPENSIVE_COURSE_EDITION = """
            SELECT id_course_edition, id_course, started_at, price, id_classroom
            FROM course_edition
            ORDER BY price DESC
            LIMIT 1
            """;
}
