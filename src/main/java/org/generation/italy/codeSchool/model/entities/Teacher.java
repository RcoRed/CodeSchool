package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;
import java.util.Set;

public class Teacher extends Person{
    private String pIVA;
    private boolean isEmployee;     //impiegato diretto dell'azienda o meno
    private LocalDate hireDate;
    private LocalDate firedDate;
    private Level level;
    private Set<Competence> competences;

}
