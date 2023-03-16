package org.generation.italy.codeSchool.model.data;

public class HibernateConstants {

    public static final String HQL_DEACTIVATE_OLDEST_N_COURSES = """
               update Course c set c.isActive=false where c in (
               select co from Course co where co.isActive = true
               order by co.createdAt
               limit :limit
            )
            """;

    public static final String HQL_OLDEST_N_COURSES = """
            from Course c
            where c.isActive=true
            order by c.createdAt
            limit :limit
            """;

    public static final String HQL_FIND_TEACHER_BY_LEVEL = """
            from Teacher t
            where t.level = :level
            """;

    public static final String HQL_FIND_TEACHER_BY_SKILL_LEVEL = """
            from Teacher t
            join t.competences comps
            where comps.skill.id = :id
                and comps.level =: level
            """;

    public static final String HQL_FIND_TEACHER_BY_N_EDITION_MODULE = """
            from Teacher t
            where t.id in(
            	select em.teacher
            	from EditionModule em
            	group by em.teacher
            	having count (*) = :n
            )
            """;

//    public static final String HQL_FIND_TEACHER_BY_SKILL_LEVEL = """
//            from Teacher t
//            where t.id in(
//                select c.person.id
//                from Competence c
//                where c.level = :level and
//                      c.skill.id = :id
//            )
//            """;

//    public static final String HQL_FIND_TEACHER_BY_SKILL_LEVEL = """
//            from Teacher t
//            where exists(
//                select 1
//                from Competence co
//                where co.level = :level and
//                    co.person = t and
//                    co.skill.id = :id
//            )
//            """;

//        public static final String HQL_DEACTIVATE_OLDEST_N_COURSES = """
//               update Course c set c.isActive=false, title = "boh" where c.id in (
//               select co.id from Course co
//            )
//            """;
}
