package org.generation.italy.codeSchool.model.data;

public class HibernateConstants {

    public static final String HQL_DEACTIVATE_OLDEST_N_COURSES = """
               update Course c set c.isActive=false where c in (
               select co from Course co where co.isActive = true
               order by co.id
               limit :limit
            )
            """;

//        public static final String HQL_DEACTIVATE_OLDEST_N_COURSES = """
//               update Course c set c.isActive=false, title = "boh" where c.id in (
//               select co.id from Course co
//            )
//            """;
}
