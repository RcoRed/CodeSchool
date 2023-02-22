package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;

public class Enrollment {
    private long id;
    private LocalDate enrollmentDate;
    private LocalDate dropoutDate;
    private Student student;
    private CourseEdition courseEdition;
    private String courseEvaluation;
    private int courseVote;
    private String studentEvaluation;
    private double studentVote;
    private boolean hasPaid;
}
