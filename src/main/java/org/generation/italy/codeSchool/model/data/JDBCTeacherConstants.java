package org.generation.italy.codeSchool.model.data;

public class JDBCTeacherConstants {
    public static final String FIND_TEACHER_BY_LEVEL = """
            SELECT
            id_person, p.firstname, p.lastname, p.dob, p.sex, p.email, p.cell_number, p.username, p.password,
            t.p_IVA, t.is_employee, t.hire_date, t.fire_date, t.level
            co.id_competence, co.level,
            id_skill, sk.name, sk.description
            FROM person AS p JOIN teacher AS t
            ON p.id_person = t.id_teacher
            JOIN competence AS co
            USING (id_person)
            JOIN skill AS sk
            USING (id_skill)
            WHERE t.level = ?
            """;
}
