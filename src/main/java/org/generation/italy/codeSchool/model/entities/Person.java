package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;
import java.util.Set;

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
    private Set<Competence> competences;

    public Person(long id, String firstname, String lastname, LocalDate dob, Sex sex, String email, String cellNumber,
                  Address address, String username, String password, Set<Competence> competences) {
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
        this.competences = competences;
    }

    public void addCompetence(Competence c) {
        competences.add(c);
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Sex getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public Address getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<Competence> getCompetences() {
        return competences;
    }

    public void setId(long id) {
        this.id = id;
    }
}
