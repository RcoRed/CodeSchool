package org.generation.italy.codeSchool.model.data;

public class JDBCConstants {
   public static final String URL ="jdbc:postgresql://localhost:5432/code_school";
   public static final String USER_NAME="postgresMaster";
   public static final String PASSWORD ="goPostgresGo";
   public static final String COURSE_QUERY = """
          SELECT course_id,title,description,course_program,duration,is_active, create_date
          FROM course
          """;
   public static final String FIND_COURSE_BY_ID = """
          SELECT course_id,title,description,course_program, duration,is_active, create_date
          FROM course WHERE course_id = ?
          """;
   public static final String DELETE_COURSE_BY_ID = """
          DELETE FROM course
          WHERE course_id = ?
          """;
   public static final String FIND_BY_TITLE_CONTAINS = """
          SELECT course_id,title,description,course_program, duration,is_active, create_date
          FROM course
          WHERE title LIKE ?
          """;
   public static final String COUNT_ACTIVES_COURSES = """
          SELECT COUNT (*) AS num_actives
          FROM course
          WHERE is_active != 0
          """;
//               """
//               SELECT course_id,title,description,course_program, duration,is_active, create_date
//               FROM course
//               WHERE is_active != 0
//               """;
   public static final String INSERT_COURSE= """
          INSERT INTO course(course_id,title,description,course_program,duration,is_active, create_date)
          VALUES(?,?,?,?,?,?,?)
          """;
   public static final String INSERT_COURSE_RETURNING_ID= """
          INSERT INTO course(course_id,title,description,course_program,duration,is_active, create_date)
          VALUES(nextval('course_id'),?,?,?,?,?,?)
          RETURNING id_course;
          """;
   public static final String NEXT_VAL_COURSE = """
          SELECT nextval('course_id');
          """;
   public static final String UPDATE_COURSE = """
          UPDATE course
          SET title = ?,          --1
          SET description = ?,    --2
          SET course_program = ?, --3
          SET duration = ?,       --4
          SET is_active = ?,      --5
          SET create_date = ?,   --6
          WHERE course_id = ?       --7
          """;
   public static final String DEACTIVATE_OLDEST = """
         UPDATE course
         SET is_active = false,
         WHERE course_id IN (SELECT course_id
                             FROM course
                             WHERE is_active IS true ORDER BY create_date LIMIT ?)
         """;
  }
//  ********** PROVA **********
//   UPDATE course
//   SET is_active = false
//   WHERE course_id IN (SELECT course_id
//					FROM course
//                     WHERE is_active IS true ORDER BY create_date LIMIT 1)
//
//SELECT *
//      FROM course
