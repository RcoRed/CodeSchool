package org.generation.italy.codeSchool.model.data.services;

public class HibernateConstants {
    public static final String HQL_FIND_TEACHER_BY_LEVEL = """
            from Teacher t
            where t.level = :level
            """;

//    public static final String HQL_FIND_TEACHER_BY_SKILL_LEVEL = """
//            from Teacher t
//            where t.id in (
//                select c.person.id from Competence c
//                where c.level = :level and c.skill.id = :id)
//            """;

//    public static final String HQL_FIND_TEACHER_BY_SKILL_LEVEL = """
//            from Teacher t
//            where exists(
//                select 1 from Competence co
//                where co.level = :level and
//                co.person = t and co.skill.id = :id)
//            """;

    public static final String HQL_FIND_TEACHER_BY_SKILL_LEVEL = """
            from Teacher t
            join t.competences comp
            where comp.skill.id = id and comp.level = :level
            """;

    public static final String HQL_FIND_TEACHER_BY_N_COURSE_EDITION = """
            from Teacher t
            t in(
                select em.teacher
                from EditionModule em
                group by em.teacher
                having count (*) = :n)
            """;
}
