package org.generation.italy.codeSchool.model.data.services;

public class HibernateConstants {
    public static final String HQL_FIND_TEACHER_BY_LEVEL = """
            from Teacher t
            where t.level = :level
            """;
}
