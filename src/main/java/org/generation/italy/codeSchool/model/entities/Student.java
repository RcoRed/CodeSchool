package org.generation.italy.codeSchool.model.entities;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Set;

public class Student extends Person{

    private LocalDate lastContact;
    private LocalDate dateOfReg;

    public Student(long id, String firstname, String lastname, LocalDate dob, Sex sex, String email, String cellNumber,
                   Address address, String username, String password, Set<Competence> competences,
                   LocalDate lastContact, LocalDate dateOfReg) {
        super(id, firstname, lastname, dob, sex, email, cellNumber, address, username, password, competences);
        this.lastContact = lastContact;
        this.dateOfReg = dateOfReg;
    }
}
