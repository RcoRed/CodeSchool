package org.generation.italy.codeSchool.model.data;

public class JDBCTeacherConstants {
   public static final String FIND_TEACHER_BY_LEVEL = """
           SELECT 
           id_person, p.firstname, p.lastname, p.dob, p.sex, p.email, p.cell_number,  p.username, p.password,
           t.p_iva, t.is_employee, t.hire_date, t.fire_date, t.level,
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
/**
 * Nuovo progettino piccolo piccolo che lavorera su uyn db con 2 tabbelle
 * impiegati e dipartimenti
 * impoiegati : id, nome cognome, data di assunzione, sesso, id dipartimento dove lavora
 * dipartimento : id, nome, indirizzo email, max capacity
 * 1. creare le due classi imp e dipartimento su java
 * 2. creare departementRepository
 *    .-metodo per inserire nuovo dipartimento
 *    .-metodo per cancellare un dipartimento con un certo id
 *    .- metodo che mi da tutti i dipartimenti che contengono una stringa nel nome,
 *       deve anche caricare tutti gli impiegati che ci lavorano (bidirezionale su Java)
 * CREARE I TEST PER TUTTI I METODI CHE FACCCIAMO
 * 3. opzionale creare 2 metodi per selezionare oggetti
 *    .- uno prende una query parametrizzata e una lambda RawMapper che descriva come
 *    mappare una riga del ResultSet e l'oggetto  da creare
 *    .-un altro metodo che prende in input una query parametrizata (?,?,?), una lambda StatementSetter
 *    che prende in input il preparedStatement e mette i parametri necessari, come 3 argomento la lambda
 *    RawMapper (senza i vari args perché ci penserà il preparedStatement)
 *
 */