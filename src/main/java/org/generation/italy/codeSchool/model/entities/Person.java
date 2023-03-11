package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;

public abstract class Person {
    protected long id;
    protected String firstname;
    protected String lastname;
    protected LocalDate dob;
    protected Sex sex;
    protected String email;
    protected String cellNumber;
    protected Address address;
    protected String username;
    protected String password;

    public Person(long id, String firstname, String lastname, LocalDate dob, Sex sex, String email, String cellNumber, Address address, String username, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.sex = sex;
        this.email = email;
        this.cellNumber = cellNumber;
        this.address = address;
        this.username = username;
        this.password = password;
    }
}
