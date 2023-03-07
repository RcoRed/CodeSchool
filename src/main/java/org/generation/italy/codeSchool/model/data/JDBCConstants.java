package org.generation.italy.codeSchool.model.data;

public class JDBCConstants {

    public static final String URL = "jdbc:postgresql://localhost:5432/code_school";
    public static final String USERNAME = "postgresMaster";
    public static final String PASSWORD = "goPostgresGo";
    public static final String FIND_BY_ID_Q = "" +
            "SELECT *" +
            "FROM course AS c" +
            "WHERE c.course_id = ?";
}
