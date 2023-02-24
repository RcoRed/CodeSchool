package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;

public class Attendance {
    private long id;
    private Lesson lesson;
    private Student student;
    private LocalDate startDate;
    private LocalDate endDate;
    private String comment;
}
