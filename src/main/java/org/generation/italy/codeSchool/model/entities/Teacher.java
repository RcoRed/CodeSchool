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


}
