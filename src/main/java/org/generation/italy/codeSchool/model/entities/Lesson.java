package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;

public class Lesson {
    private long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Classroom classRoom;
    private Teacher teacher;
}
