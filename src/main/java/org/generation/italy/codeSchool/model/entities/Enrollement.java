package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;

public class Enrollement {
   private long id;
   private LocalDate enrollementDate;
   private LocalDate dropoutDate;
   private Student student;
   private CourseEdition courseEdition;
   private String courseEvaluation;
   private int courseVote;
   private String studentEvaluation;
   private double studentVote;
   private boolean hasPaid;
}
