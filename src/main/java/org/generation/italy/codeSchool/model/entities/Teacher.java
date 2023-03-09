package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;
import java.util.Set;

public class Teacher extends Person{
    private String pIVA;
    private boolean isEmployee;
    private LocalDate hireDate;
    private LocalDate fireDate;
    private Level level;
    private Set<Competence> competences;

    public Teacher(long id, String firstname, String lastname, LocalDate dob, Sex sex, String email, String cellNumber, Address address, String username, String password,
                   String pIVA, boolean isEmployee, LocalDate hireDate, LocalDate fireDate, Level level, Set<Competence> competences) {
        super(id,firstname,lastname,dob,sex,email,cellNumber,address,username,password);
        this.pIVA = pIVA;
        this.isEmployee = isEmployee;
        this.hireDate = hireDate;
        this.fireDate = fireDate;
        this.level = level;
        this.competences = competences;
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

    public Set<Competence> getCompetences() {
        return competences;
    }
}
