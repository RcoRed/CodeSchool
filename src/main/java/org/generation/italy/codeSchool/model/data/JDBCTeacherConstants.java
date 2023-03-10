package org.generation.italy.codeSchool.model.data;

public class JDBCTeacherConstants {
    public static final String FIND_TEACHER_BY_LEVEL = """
            SELECT
            id_person, p.firstname, p.lastname, p.dob, p.sex, p.email, p.cell_number, p.username, p.password,
            t.p_IVA, t.is_employee, t.hire_date, t.fire_date, t.level,
            co.id_competence, co.level,
            id_skill, sk.name AS skill_name, sk.description as skill_description
            FROM person AS p JOIN teacher AS t
            ON p.id_person = t.id_teacher
            JOIN competence AS co
            USING (id_person)
            JOIN skill AS sk
            USING (id_skill)
            WHERE t.level = ?
            """;
}
/*
* Nuovo progetto
* Lavorerà su un nuovo DB con impiegati e dipartimenti
* impiegati: id, nome, cognome, data di assunzione, sesso, id dipartimento
* dipartimento: id, nome, indirizzo, max capacity
* 1. creaimo le due classi impiegato e dipartimento
* 2. creare un DepartmentRepository che avrà:
*   a. Un metodo per inserire un nuovo dipartimento
*   b. Un metodo per cancellare un dato dipartimento con un certo id
*   c. Un metodo che mi da tutti i dipartimenti che contengono una data stringa nel nome,
*       ma quest'ultimo deve anche caricare tutti gli impiegati che ci lavorano
* 2a. fare i test per tutti i metodi che riusciamo a fare
* Quindi la classe Dipartimento avrà un Set di impiegati e ogni Impiegato avrà un private Dipartimento
* 3. (Opzionale) Creare due metodi per selezionare oggetti:
*   a. uno prende una query parametrizzata, una lambda RawMapper che descriva come mappare una riga del ResultSet e l'oggeto da creare, e i var args relativi all'oggetto da creare
*   b. un altro prende una query parametrizzata, una lambda StatementSetter che prende in input il PreparedStatement e ne setta i parametri necessari, infine la lambda RawMapper (senza i var args perché ci penserà StatementSetter)
*
* */
