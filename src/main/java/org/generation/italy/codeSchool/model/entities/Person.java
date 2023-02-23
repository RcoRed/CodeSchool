package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;

public abstract class Person {
   protected long id;
   protected String firstname;
   protected String lastname;
   protected LocalDate dob; //Date Of Birth
   protected Sex sex;
   protected String email;
   protected String cellNumber;
   protected Address address;
   private String username;
   private String password;

}
