package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;
@Entity
@PrimaryKeyJoinColumn(name = "id_teacher")
@Table(name = "teacher")
public class Teacher extends Person{
    @Column(name = "p_iva")
    private String pIVA;
    @Column(name = "is_employee")
    private boolean isEmployee;
    @Column(name = "hire_date")
    private LocalDate hireDate;
    @Column(name = "fire_date")
    private LocalDate fireDate;
    @Enumerated(EnumType.STRING)
    private Level level;
    public Teacher(){}
    public Teacher(long id, String firstname, String lastname, LocalDate dob, Sex sex, String email, String cellNumber,
                   Address address, String username, String password, Set<Competence> competences, String pIVA, boolean isEmployee,
                   LocalDate hireDate, LocalDate fireDate, Level level) {
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

}
