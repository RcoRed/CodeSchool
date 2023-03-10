package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;
import java.util.Set;

public class Teacher extends Person{
    private String pIVA;
    private boolean isEmployee;
    private LocalDate hireDate;
    private LocalDate fireDate;
    private Level level;


    public Teacher(long id, String firstname, String lastname, LocalDate dob, Sex sex, String email, String cellNumber,
                   Address address, String username, String password,Set<Competence> competences,String pIVA,
                   boolean isEmployee, LocalDate hireDate, LocalDate fireDate, Level level) {
        super(id, firstname, lastname, dob, sex, email, cellNumber, address, username, password, competences);
        this.pIVA = pIVA;
        this.isEmployee = isEmployee;
        this.hireDate = hireDate;
        this.fireDate = fireDate;
        this.level = level;
    }


    public String getpIVA() {
        return pIVA;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public LocalDate getFireDate() {
        return fireDate;
    }

    public Level getLevel() {
        return level;
    }

    public void setpIVA(String pIVA) {
        this.pIVA = pIVA;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public void setFireDate(LocalDate fireDate) {
        this.fireDate = fireDate;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
