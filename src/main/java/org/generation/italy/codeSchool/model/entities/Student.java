package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table (name = "student")
@PrimaryKeyJoinColumn (name = "id_student")
public class Student extends Person{
    @Column (name = "last_contact")
    private LocalDate lastContact;
    @Column (name = "date_of_reg")
    private LocalDate dateOfReg;

    public Student() {

    }

    public Student(long id, String firstname, String lastname, LocalDate dob, Sex sex, String email, String cellNumber,
                   Address address, String username, String password, Set<Competence> competences,
                   LocalDate lastContact, LocalDate dateOfReg) {
        super(id, firstname, lastname, dob, sex, email, cellNumber, address, username, password, competences);
        this.lastContact = lastContact;
        this.dateOfReg = dateOfReg;
    }

    public LocalDate getLastContact() {
        return lastContact;
    }

    public LocalDate getDateOfReg() {
        return dateOfReg;
    }
}
