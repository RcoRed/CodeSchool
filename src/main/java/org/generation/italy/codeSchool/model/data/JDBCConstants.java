package org.generation.italy.codeSchool.model.data;

public class JDBCConstants {
    public static final String URL ="jdbc:postgresql://localhost:5432/code_school";
    public static final String USER_NAME="postgresMaster";
    public static final String PASSWORD ="goPostgresGo";
    public static final String COURSE_QUERY="""
                SELECT id,title,description,program,duration,is_active,created_at
                FROM course""";
    public static final String FIND_COURSE_BY_ID ="""
                SELECT id,title,description,program, duration,is_active,created_at
                FROM course WHERE id = ? 
                """;
    public static final String DELETE_COURSE_BY_ID= """
               DELETE FROM course
               WHERE id = ?
            """;

}
